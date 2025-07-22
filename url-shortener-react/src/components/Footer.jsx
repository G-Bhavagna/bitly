import React from "react";
import {
  FaFacebook,
  FaTwitter,
  FaInstagram,
  FaLinkedin,
} from "react-icons/fa";

const Footer = () => {
  return (
    <footer className="bg-[linear-gradient(to_right,_#3b82f6,_#9333ea)] text-white py-8 z-40 relative font-montserrat">
      <div className="container mx-auto px-6 lg:px-14 flex flex-col lg:flex-row lg:justify-between items-center gap-4">
        <div className="text-center lg:text-left">
          <h2 className="text-3xl font-bold mb-2">Linklytics</h2>
          <p className="text-sm">Simplifying URL shortening for efficient sharing</p>
        </div>

        <p className="mt-4 lg:mt-0 text-sm text-white/80">
          &copy; 2024 Linklytics. All rights reserved.
        </p>

        <div className="flex space-x-6 mt-4 lg:mt-0 text-white">
          <a href="#" className="hover:text-[#2e1065] transition-colors">
            <FaFacebook size={24} />
          </a>
          <a href="#" className="hover:text-[#2e1065] transition-colors">
            <FaTwitter size={24} />
          </a>
          <a href="#" className="hover:text-[#2e1065] transition-colors">
            <FaInstagram size={24} />
          </a>
          <a href="#" className="hover:text-[#2e1065] transition-colors">
            <FaLinkedin size={24} />
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
