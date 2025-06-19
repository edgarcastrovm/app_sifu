require('dotenv').config();
const express = require('express');
const session = require('express-session');
const bodyParser = require('body-parser');
const path = require('path');
const axios = require('axios');

const app = express();

// Configuración
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(session({
  secret: 'your-secret-key',
  resave: false,
  saveUninitialized: true,
  cookie: { secure: false }
}));

// Configuración de la API Java
//const JAVA_API = 'http://localhost:8080/api';

// Middleware para variables globales
app.use((req, res, next) => {
  res.locals.currentUser = req.session.user || null;
  next();
});

// Rutas
const publicRoutes = require('./routes/publicRoutes');
const authRoutes = require('./routes/authRoutes');
const productRoutes = require('./routes/productRoutes');
const productorsRoutes = require('./routes/productorsRoutes');
const cartRoutes = require('./routes/cartRoutes');

app.use('/', publicRoutes);
app.use('/login', authRoutes);
app.use('/productos', productRoutes);
app.use('/productors', productorsRoutes);
app.use('/carrito', cartRoutes);

// Iniciar servidor
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`);
});