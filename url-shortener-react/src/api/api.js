import axios from "axios";
import { base } from "framer-motion/client";

export default axios.create({
  baseURL: import.meta.env.VITE_BACKEND_URL,
});