const express = require('express');
const router = express.Router();
const productController = require('../controllers/productController');
const { isAuthenticated } = require('../middlewares/auth');

router.get('/', productController.listProductsView);
router.get('/api/listar', productController.listProducts);
router.get('/:id', isAuthenticated, productController.getProductDetails);
router.post('/api/productos', productController.getProductDetails);

module.exports = router;
