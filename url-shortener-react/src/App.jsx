import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Routes,Route } from 'react-router-dom'
import LandingPage from './components/LandingPage'
import AboutPage from './components/AboutPage'
import Navbar from './components/Navbar'
import Footer from './components/Footer'
import RegisterPage from './components/RegisterPage'
import { Toaster } from 'react-hot-toast'
import LoginPage from './components/LoginPage'
import DashboardLayout from './components/dashboard/DashboardLayout'
function App() {


  return (
<>
<Navbar/>
<Toaster position='bottom-center'/>
  <Routes>
    <Route path="/" element={<LandingPage />} />
    <Route path="/about" element={<AboutPage />} />
    <Route path="/register" element={<RegisterPage />} />
    <Route path='/login' element={<LoginPage/>}/>
    <Route path="/dashboard" element={<DashboardLayout />} />
  </Routes>
  <Footer />
</>
  )
}

export default App
