const express = require('express');
const router = express.Router();
const cartController = require('../controllers/cartController');
const { isAuthenticated } = require('../middlewares/auth');

router.get('/', isAuthenticated, cartController.viewCart);
router.post('/agregar', isAuthenticated, cartController.addToCart);
router.delete('/eliminar/:itemId', isAuthenticated, cartController.removeFromCart);

module.exports = router;