const javaApi = require('../config/db');

exports.registryViewProductors = async (req, res) => {
  try {
    res.render('productors/registry');
  } catch (error) {
    console.error('Error al renderizar vista registro productores:', error);
    res.status(500).render('error', { message: 'Error al obtener vista registro productores' });
  }
};


exports.registryProductors = async (req, res) => {

  try {
    const data = req.body;
    console.log('Datos recibidos para registrar:', data);

    const response = await javaApi.post('/api/public/registry/producer', data);
    
    const body = response.data;
    const item = body.data;
    console.log('Respuesta de la API al registrar un productor: ', item);
    res.status(200).json({ message: 'Productor registrado correctamente' });
  } catch (error) {
    console.error('Error al registrar productor:', error);
    res.status(500).json({ message: 'Error al registrar el productor' });
  }
};

exports.listProductorsView = (req, res) => {
  try {
    res.render('productors/list');
  } catch (error) {
    console.error('Error al renderizar vista lista productores:', error);
    res.status(500).render('error', { message: 'Error al obtener vista lista productores' });
  }
};

exports.listProductors = async (req, res) => {
  try {
    // Llamar a la API Java para listar productores
    const response = await javaApi.get('/api/public/showroom/productors');
    const body = response.data;
    const item = body.data;

    console.log('Respuesta de la API:', response.data);
    
    return res.json(item);
  } catch (error) {
    console.error('Error obteniendo productores:', error);
    res.json([]);
  }
};


exports.listProductView = (req, res) => {
  try {
    res.render('productors/listProduct');
  } catch (error) {
    console.error('Error al renderizar vista lista productores:', error);
    res.status(500).render('error', { message: 'Error al obtener vista lista productores' });
  }
};

exports.registryProducts = async (req, res) => {

  try {
    const data = req.body;
    console.log('Datos recibidos para registrar:', data);

    const response = await javaApi.post('/api/public/registry/producer', data);
    
    const body = response.data;
    const item = body.data;
    console.log('Respuesta de la API al registrar un productor: ', item);
    res.status(200).json({ message: 'Productor registrado correctamente' });
  } catch (error) {
    console.error('Error al registrar productor:', error);
    res.status(500).json({ message: 'Error al registrar el productor' });
  }
};