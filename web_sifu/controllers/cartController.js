const javaApi = require('../config/db');

exports.viewCart = async (req, res) => {
  try {
    const response = await javaApi.get('/api/v1/shop/getItemCart', {
      headers: {
        'Authorization': `Bearer ${req.session.token}`
      }
    });
    
    const body = response.data;
    const items = body.data;
    console.log('Respuesta de la API al agregar al carrito:', items);
    
    res.render('cart/view', { items: items });
  } catch (error) {
    console.error('Error al obtener carrito:', error);
    res.status(500).render('error', { message: 'Error al obtener carrito' });
  }
};

exports.addToCart = async (req, res) => {
  try {
    const { proId, catidad } = req.body;
    console.log('Datos recibidos para agregar al carrito:', { proId, catidad });

    const response = await javaApi.post('/api/v1/shop/addItemCart', {
      proId: proId,
      catidad: catidad || 1
    }, {
      headers: {
        'Authorization': `Bearer ${req.session.token}`
      }
    });
    
    const body = response.data;
    const item = body.data;
    console.log('Respuesta de la API al agregar al carrito:', item);
    res.redirect('/carrito');
  } catch (error) {
    console.error('Error al agregar al carrito:', error);
    res.status(500).render('error', { message: 'Error al agregar al carrito' });
  }
};

exports.removeFromCart = async (req, res) => {
  try {
    const { itemId } = req.params;
    
    await javaApi.delete(`/carrito/eliminar/${itemId}`, {
      headers: {
        'Authorization': `Bearer ${req.session.token}`
      }
    });
    
    res.redirect('/carrito');
  } catch (error) {
    console.error('Error al eliminar del carrito:', error);
    res.status(500).render('error', { message: 'Error al eliminar del carrito' });
  }
};