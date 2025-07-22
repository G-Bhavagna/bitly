import React from 'react'
import { useForm } from 'react-hook-form'
import TextField from './TextField';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import api from '../api/api';
import toast from 'react-hot-toast';
import { useStoreContext } from '../contextApi/ContextAPI';

const LoginPage = () => {
  const navigate = useNavigate();
  const [loader, setLoader] = useState(false);
  const {setToken}=useStoreContext();


  const{
    register,
    handleSubmit,
    reset,
    formState: { errors },
  }= useForm({
    defaultValues: {
      username:'',
      password: '',
  },
  mode: 'onTouched',
})

  const loginHandler = async (data) => {
      setLoader(true);
      try{
       
        const {data: response}= await api.post('/api/auth/public/login', data);
        console.log(response.token);
        setToken(response.token);
        localStorage.setItem('token', JSON.stringify(response.token));
        reset();
        navigate('/dashboard');
      
        toast.success("Login successful");
    }catch (error) {
      console.error("Login  error:", error);
      toast.error("Login failed, please try again");
    }finally {
      setLoader(false);
    }
  };

  return (
    <div className='min-h-[calc(100vh-64px)] flex items-center justify-center'>
    <form
  onSubmit={handleSubmit(loginHandler)}
className="sm:w-[450px] w-[360px] bg-white shadow-lg shadow-gray-400/30 rounded-2xl py-10 px-6">
    <h1 className="text-center font-serif text-blue-600 font-bold lg:text-3xl text-2xl">

                Login
            </h1>
             <hr className='mt-2 mb-5 text-amber-50'/>

            <div className="flex flex-col gap-3">
                <TextField
                    label="UserName"
                    required
                    id="username"
                    type="text"
                    message="*Username is required"
                    placeholder="Type your username"
                    register={register}
                    errors={errors}
                />
                <TextField
                    label="Password"
                    required
                    id="password"
                    type="password"
                    message="*Password is required"
                    placeholder="Type your password"
                    register={register}
                    min={6}
                    errors={errors}
                />
              
              </div>
               <button
                disabled={loader}
                type='submit'
                className='bg-customRed font-semibold bg-[linear-gradient(to_right,_#3b82f6,_#9333ea)] w-40 text-white py-2 rounded-md w-full py-2 hover:text-slate-400 transition-colors duration-100 rounded-sm my-3'>
                {loader ? "Loading..." : "Login"}
            </button>

            <p className='text-center text-sm text-slate-700 mt-6'>
                Don't have an account?  
                <Link
                    className='font-semibold underline hover:text-black'
                    to="/register">
                        <span className='text-btnColor'> Register</span>
                </Link>
            </p>


      </form>
    </div>
  )
}

export default LoginPage