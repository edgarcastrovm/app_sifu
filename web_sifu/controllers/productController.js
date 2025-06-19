const javaApi = require('../config/db');

exports.listProductsView = async (req, res) => {
  try {
    res.render('products/list');
  } catch (error) {
    console.error('Error al obtener productos:', error);
    res.status(500).render('error', { message: 'Error al obtener vista productos' });
  }
};


exports.listProducts = async (req, res) => {
  try {
    const response = await javaApi.get('/api/public/showroom/products');
    const body = response.data;
    const item = body.data;

    console.log('Respuesta de la API:', response.data);
    
    return res.json(item);
  } catch (error) {
    console.error('Error en listProducts:', error);
    res.render('error', { message: 'Error al obtener lista productos' });
  }
};

exports.listProductsByCatalog = async (req, res) => {
  try {
    const response = await javaApi.get('/api/public/showroom/products/catalog');
    const body = response.data;
    const item = body.data;

    console.log('Respuesta de la API:', response.data);
    
    return res.json(item);
  } catch (error) {
    console.error('Error en listProductsByCatalog:', error);
    res.render('error', { error: 'Error al obtener lista productos' });
  }
};

exports.listProductsByProductor = async (req, res) => {
  try {
    const { id } = req.params;
    const response = await javaApi.get(`/api/public/showroom/products/productors/${id}`);
    const body = response.data;
    const item = body.data;

    console.log('Respuesta de la API:', response.data);
    
    return res.json(item);
  } catch (error) {
    console.error('Error en listProductsByCatalog:', error);
    res.render('error', { error: 'Error al obtener lista productos' });
  }
};

exports.getProductDetails = async (req, res) => {
  try {
    const { id } = req.params;
    const response = await javaApi.get(`/api/public/showroom/products/${id}`);

    res.render('products/details', { producto: response.data });
  } catch (error) {
    console.error('Error al obtener detalles del producto:', error);
    res.status(500).render('error', { message: 'Error al obtener detalles del producto' });
  }
};