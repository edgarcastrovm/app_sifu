exports.isAuthenticated = (req, res, next) => {
    if (req.session.user) {
      return next();
    }
    res.redirect('/login');
  };
  
  exports.isAdmin = (req, res, next) => {
    //if (req.session.user && req.session.user.rol === 'ADMIN') {
    if (req.session.user) {
      return next();
    }
    res.status(403).render('error', { message: 'Acceso no autorizado' });
  };