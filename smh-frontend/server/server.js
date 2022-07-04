const jsonServer = require('json-server');
const server = jsonServer.create();
const path = require('path');
const db = require(path.join(__dirname, 'db.json'));
const router = jsonServer.router(db);
const middlewares = jsonServer.defaults();

// Set default middlewares (logger, static, cors and no-cache)
server.use(middlewares);

// To handle POST, PUT and PATCH you need to use a body-parser
// You can use the one used by JSON Server
server.use(jsonServer.bodyParser);

// Add custom routes before JSON Server router
server.post('/api/login', (req, res) => {
  const { users } = db;

  const userInRegistry = req.body && users.find(user => user.email === req.body.email && user.password === req.body.password);

  if (userInRegistry) {
    res.jsonp(userInRegistry);
  } else {
    const errorCode = 401;

    res.status(errorCode).send({ code: errorCode, message: 'Введен неверный пароль или логин' });
  }
});

// Use default router
server.use('/api', router);
server.listen(5000, () => {
  console.log('JSON Server is running');
});
