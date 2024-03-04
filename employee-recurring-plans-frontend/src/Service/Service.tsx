import axios from "axios";

const BASE_API_URL ="http://localhost:8082/contributions";

interface User {
    name: string;
    emailId: string;
    password: string;
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

interface CreatePlanByUser{
    id:string,
    dob: Date | null,
    salary:number,
    self_contribution_limit_401K:number,
    self_contribution_limit_HSA:number
    self_contribution_limit_FSA:number
    self_contribution_limit_ROTHIRA: number 
}

export const createUserPlan=(createUserPlanByData: CreatePlanByUser)=>{
    return axios.post(`${BASE_API_URL}/createUserPlan`,createUserPlanByData,authConfig)
}

interface CreateUserPlanByEmployer{
    id:string,
    employer_contribution_limit_401k:number,
    employer_contribution_limit_HSA:number
    employer_contribution_limit_FSA:number
    employer_contribution_limit_ROTHIRA: number
}

export const createUserPlanByEmployer=(createUserPlanByEmployerData:CreateUserPlanByEmployer)=>{
    return axios.post(`${BASE_API_URL}/createUserPlanByEmployer`,createUserPlanByEmployerData,authConfig)
}
export const getAllUsersPlan = () =>{
    return axios.get(`${BASE_API_URL}/getAllUsersPlan`,authConfig);
}
export const getUserPlanById= (id: string)=>{
    return axios.get(`${BASE_API_URL}/getUserPlanById/${id}`,authConfig);
}

interface EditRecurringPlanByUser{
    salary:number,
    self_contribution_limit_401K:number,
    self_contribution_limit_HSA:number
    self_contribution_limit_FSA:number
    self_contribution_limit_ROTHIRA: number 
}


export const editEmployerContributions= (id: string,editRecurringByUserData:EditRecurringPlanByUser)=>{
    return axios.patch(`${BASE_API_URL}/editEmployerContributions/${id}`,editRecurringByUserData,authConfig);
}

interface EditUserRecurringPlanByEmployer{
    employer_contribution_limit_401k:number,
    employer_contribution_limit_HSA:number
    employer_contribution_limit_FSA:number
    employer_contribution_limit_ROTHIRA: number
}
export const editUserContributions= (id: string,editUserRecurringPlanByEmployerData:EditUserRecurringPlanByEmployer)=>{
    return axios.patch(`${BASE_API_URL}/editUserContributions/${id}`,editUserRecurringPlanByEmployerData,authConfig);
    
}