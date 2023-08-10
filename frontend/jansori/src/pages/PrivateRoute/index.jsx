// src/Router/PrivateRoute.jsx
import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ authenticated, component: Component }) => {
  return authenticated ? Component : <Navigate to="/login" />;
};

export default PrivateRoute;
