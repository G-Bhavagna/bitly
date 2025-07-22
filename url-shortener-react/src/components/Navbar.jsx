import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { IoIosMenu } from "react-icons/io";
import { RxCross2 } from "react-icons/rx";

const Navbar = () => {
  const path = useLocation().pathname;
  const [navbarOpen, setNavbarOpen] = useState(false);

  return (
    <div className="h-16 bg-[linear-gradient(to_right,_#3b82f6,_#9333ea)] z-50 flex items-center sticky top-0">
      <div className="lg:px-14 sm:px-8 px-4 w-full flex justify-between">
        <Link to="/">
          <h1 className="font-bold text-3xl text-white italic sm:mt-0 mt-2">
            Linklytics
          </h1>
        </Link>
        <ul
          className={`flex sm:gap-10 gap-4 sm:items-center sm:mt-1 sm:pt-0 pt-3 text-slate-800 sm:static absolute left-0 top-[62px] sm:shadow-none shadow-md ${
            navbarOpen ? "h-fit sm:pb-0 pb-5" : "h-0 overflow-hidden"
          } transition-all duration-100 sm:h-fit sm:bg-none bg-[linear-gradient(to_right,_#3b82f6,_#9333ea)] sm:w-fit w-full sm:flex-row flex-col px-4 sm:px-0`}
        >
          <li className="hover:text-[#9333ea] font-[500] transition-all duration-150">
            <Link
              className={`${
                path === "/" ? "text-white font-semibold" : "text-gray-200"
              }`}
              to="/"
            >
              Home
            </Link>
          </li>
          <li className="hover:text-[#9333ea] font-[500] transition-all duration-150">
            <Link
              className={`${
                path === "/about" ? "text-white font-semibold" : "text-gray-200"
              }`}
              to="/about"
            >
              About
            </Link>
          </li>
          <li className="sm:ml -0  -m1-1 bg-rose-700 cursor-pointer hover:bg-rose-600 transition-colors duration-150 rounded-md sm:px-4 px-2 py-1">
            <Link
              className={`${
                path === "/register" ? "text-white font-semibold" : "text-gray-200"
              }`}
              to="/register"
            >
              SignUp
            </Link>
          </li>
        </ul>
        <button
          onClick={() => setNavbarOpen(!navbarOpen)}
          className="sm:hidden flex items-center sm:mt-0 mt-2"
        >
          {navbarOpen ? (
            <RxCross2 className="text-white text-3xl" />
          ) : (
            <IoIosMenu className="text-white text-3xl" />
          )}
        </button>
      </div>
    </div>
  );
};

export default Navbar;
