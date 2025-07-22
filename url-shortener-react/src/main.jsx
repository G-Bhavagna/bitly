import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { BrowserRouter } from 'react-router-dom'
import { ContextProvider } from './contextApi/ContextAPI.jsx'
import { QueryClientProvider,QueryClient } from '@tanstack/react-query'

const queryClient = new QueryClient();
createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
    <QueryClientProvider client={queryClient}>
 <ContextProvider>
      <App />
    </ContextProvider>
   
    </QueryClientProvider>
   
  </BrowserRouter>
    
  </StrictMode>,
)
