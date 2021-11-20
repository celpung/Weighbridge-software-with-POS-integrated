import axios from "axios";
import store from '../store/index'

export default axios.create({
 baseURL: "https://shrcell.com/public/api/",
 //baseURL: "http://localhost:8000/api/",
 headers: {
  "Authorization": `Bearer ${store.state.token}`,
  "Content-type": "application/json",
 },
 //withCredentials: true,
});