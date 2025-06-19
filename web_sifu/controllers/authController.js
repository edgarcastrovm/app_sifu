const javaApi = require('../config/db');
const CryptoJS =require('crypto-js');


exports.loginForm = (req, res) => {
  res.render('auth/login');
};

exports.login = async (req, res) => {
  try {
    let  { username, password } = req.body;
    password =  CryptoJS.MD5(password).toString() || '';
    // Llamar a la API Java para autenticación
    const response = await javaApi.post('/api/public/login', {
      email:username,
      password
    });
    const body = response.data;
    const item = body.data;

    console.log('Respuesta de la API:', response.data);

    // Guardar usuario en sesión
    req.session.user = {
      id:item.id,
      email:item.email,
      token:item.token,
      rol:item.rol
    };
    
    res.redirect('/productos');
  } catch (error) {
    console.error('Error en login:', error);
    res.render('auth/login', { error: 'Credenciales inválidas' });
  }
};



exports.logout = (req, res) => {
  req.session.destroy();
  res.redirect('/');
};