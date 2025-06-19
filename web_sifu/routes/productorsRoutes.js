const express = require('express');
const router = express.Router();
const productorsController = require('../controllers/productorController');
const productController = require('../controllers/productController');

router.get('/', productorsController.listProductorsView);
router.get('/api/listar', productorsController.listProductors);
router.get('/api/products/:id', productController.listProductsByProductor);

router.get('/product', productorsController.listProductView);
router.post('/api/product', productorsController.registryProducts);

router.get('/registry', productorsController.registryViewProductors);
router.post('/api/registry', productorsController.registryProductors);

module.exports = router;
