import { createContext, useState, useEffect, useContext } from "react";

const ContextApi = createContext();

export const ContextProvider = ({ children }) => {
  const [token, setToken] = useState(null);

  // ✅ Load token from localStorage when app starts
  useEffect(() => {
    const storedToken = localStorage.getItem("JWT_TOKEN");
    if (storedToken) {
      setToken(JSON.parse(storedToken));
    }
  }, []);

  // Optional: Update localStorage when token changes
  useEffect(() => {
    if (token) {
      localStorage.setItem("JWT_TOKEN", JSON.stringify(token));
    }
  }, [token]);

  return (
    <ContextApi.Provider value={{ token, setToken }}>
      {children}
    </ContextApi.Provider>
  );
};

export const useStoreContext = () => {
  const context = useContext(ContextApi);
  return context;
};
 