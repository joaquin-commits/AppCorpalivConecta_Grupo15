// API académica local. Node.js 18+; sin paquetes externos. Solo datos ficticios.
const http = require('node:http');
const fs = require('node:fs');
const path = require('node:path');
const fields = ['nombre','correo','organizacion','tipo','mensaje','ubicacion','fecha','contexto'];
function valid(raw) {
  if (!raw || typeof raw !== 'object' || Array.isArray(raw)) return null;
  const s = Object.fromEntries(fields.map(k => [k, raw[k] ?? '']));
  if (!fields.every(k => typeof s[k] === 'string') || s.nombre.trim().length < 3 || !/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/.test(s.correo.trim()) || !s.tipo.trim() || s.mensaje.trim().length < 10) return null;
  return s;
}
function createServer(file = path.join(__dirname, 'solicitudes.json')) {
  const load = () => fs.existsSync(file) ? JSON.parse(fs.readFileSync(file,'utf8')) : {next:1,items:[]};
  const save = db => { fs.writeFileSync(file+'.tmp', JSON.stringify(db,null,2)); fs.renameSync(file+'.tmp',file); };
  return http.createServer(async (req,res) => {
    const reply = (code,body) => {res.writeHead(code, {'Content-Type':'application/json; charset=utf-8'});res.end(body === undefined ? undefined : JSON.stringify(body));};
    const match = /^\/solicitudes(?:\/([1-9][0-9]*))?\/?$/.exec(req.url);
    if (!match) return reply(404,{error:'Ruta no encontrada'});
    const id = match[1] ? Number(match[1]) : null;
    const m = req.method;
    if (!(id === null ? ['GET','POST'] : ['PUT','DELETE']).includes(m)) return reply(405,{error:'Método no permitido'});
    let s;
    if (m==='POST'||m==='PUT') {
      try {let chunks=[],n=0;for await (const chunk of req) {n+=chunk.length;if(n>65536) return reply(413,{error:'Solicitud demasiado grande'});chunks.push(chunk);}s=valid(JSON.parse(Buffer.concat(chunks).toString()));} catch {return reply(400,{error:'JSON inválido'});}
      if (!s) return reply(400,{error:'Solicitud inválida'});
    }
    try {
      const db=load();
      if(m==='GET') return reply(200,db.items);
      if(m==='POST') {const created={...s,id:db.next++};db.items.unshift(created);save(db);return reply(201,created);}
      const index=db.items.findIndex(x=>x.id===id);
      if(index<0) return reply(404,{error:'Solicitud no encontrada'});
      if(m==='PUT') {const edited={...s,id};db.items[index]=edited;save(db);return reply(200,edited);}
      db.items.splice(index,1);save(db);return reply(204);
    } catch {return reply(500,{error:'No se pudo acceder al almacenamiento'});}
  });
}
if(require.main===module) {const host=process.env.CORPALIV_HOST||'127.0.0.1';createServer().listen(8080,host,()=>console.log(`API CORPALIV: http://${host}:8080/solicitudes`));}
module.exports={createServer,valid};
