const express = require('express');
const path = require('path');
const app = express();

// Serve arquivos estáticos da pasta dist (gerada pelo build do Vue)
app.use(express.static(path.join(__dirname, 'dist')));

// Roteamento para todas as URLs, redirecionando para index.html
app.get('*', (req, res) => {
  res.sendFile(path.resolve(__dirname, 'dist', 'index.html'));
});

// Configura a porta
const port = 8083;
app.listen(port, () => {
  console.log(`Servidor rodando em http://localhost:${port}`);
});
