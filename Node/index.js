const express = require('express');
const { rootCertificates } = require('tls');
const app = express()
const port = 3001;
const config =  {
    host: 'my-mysql',
    user: 'root',
    password:'********',
    database: '************'
};
const mysql = require('mysql')
const connection = mysql.createConnection(config)

const sql = 'INSERT INTO people(name) values("Edivan")'
connection.query(sql)
connection.end

app.get('/',(req,res)=>{
    res.send('<h1>Edivan</h1>')
})

app.listen(port,()=>{
    console.log('Rodando na porta:' + port)
})