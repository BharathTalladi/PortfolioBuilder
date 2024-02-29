import axios from "axios";

const BASE_API_URL ="http://localhost:8082/contributions";

interface User {
    name: string;
    emailId: string;
    password: string;
}
interface AdminUser {
    name: string;
    emailId: string;
    password: string;
    roles:string;
}
interface login{
    id:string;
    password:string;
}

const config = {
    headers: {
        'Content-Type': 'application/json'
    }
};

export const registerUser = (userData : User) =>{
    return axios.post(`${BASE_API_URL}/register`, userData, config);
}
export const registerAdminUser = (adminUserData: AdminUser)=>{
    return axios.post(`${BASE_API_URL}/register`,adminUserData,config);
}
export const storeToken = (token: string)=>{
    return localStorage.setItem("token",token);
}
export const getToken = ()=>{
    return localStorage.getItem("token");
}
export const loginUser = (userLogin : login) => {
    return axios.post(`${BASE_API_URL}/login`,userLogin, config);
};
export const logoutUser=()=>{
    localStorage.clear();
    sessionStorage.clear();
}
export const isAdminUser = ()=>{
    let role=localStorage.getItem("role"); 
    if(role!=null && role== 'ADMIN')
    {
        return true; 
    }
    else{
        return false;
    }
}
export const saveLoggedInUser = (id: string,role: string) =>{
    localStorage.setItem("authenticatedUser",id);
    localStorage.setItem("role",role);

}
export const isUserLoggedIn = () =>{
    const id=localStorage.getItem("authenticatedUser");
    if(id==null)
    {return false;}
    else{return true;}
}
export const getLoggedInUser = () =>{
    const id=localStorage.getItem("authenticatedUser");
    return id;
}
//Request Interceptor
axios.interceptors.request.use(function (config){
    config.headers['Authorization'] =getToken();
    return config;
   },function (error){ 
    return Promise.reject(error);
});

const authConfig = {
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `${getToken()}`
    }
};
export const createUserPlan=()=>{
    return axios.post(`${BASE_API_URL}/createUserPlan`,authConfig)
}
export const createUserPlanByEmployer=()=>{
    return axios.post(`${BASE_API_URL}/createUserPlanByEmployer`,authConfig)
}
export const getAllUsersPlan = () =>{
    return axios.get(`${BASE_API_URL}/getAllUsersPlan`,authConfig);
}
export const getUserPlanById= (id: string)=>{
    console.log(authConfig);
    return axios.get(`${BASE_API_URL}/getUserPlanById/${id}`,authConfig);
}
export const editEmployerContributions= (id: string)=>{
    
    return axios.get(`${BASE_API_URL}/editEmployerContributions/${id}`,authConfig);
    
}
export const editUserContributions= (id: string)=>{
    return axios.get(`${BASE_API_URL}/editUserContributions/${id}`,authConfig);
    
}