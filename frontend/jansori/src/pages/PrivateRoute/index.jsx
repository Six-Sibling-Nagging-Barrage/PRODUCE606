import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const PrivateRoute = ({ children }) => {
  const { state } = useLocation();
  const token = localStorage.getItem('member_access_token');

  return state || token ? children : <Navigate to="/login" />;
};

export default PrivateRoute;
