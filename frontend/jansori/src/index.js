import React from 'react';
import ReactDOM from 'react-dom/client';
import { RecoilRoot } from 'recoil';
import './index.css';
import App from './App';
import GlobalStyles from './styles/GlobalStyles';
import { BrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <RecoilRoot>
      <QueryClientProvider client={queryClient}>
        <GlobalStyles />
        <App />
      </QueryClientProvider>
    </RecoilRoot>
  </BrowserRouter>
);
