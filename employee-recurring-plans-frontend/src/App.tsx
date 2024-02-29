import './App.css'
import FooterComponent from './Components/FooterComponent'
import HeaderComponent from './Components/HeaderComponent'
import LoginComponent from './Components/LoginComponent'
import {Routes,Route,Navigate } from 'react-router-dom'
import { isUserLoggedIn } from './Service/Service'
import RegisterComponent from './Components/RegisterComponent'
import UserPlanComponent from './Components/UserPlanComponent'
import EmployerUserPlansComponent from './Components/EmployerUserPlansComponent'
import EditEmployerContributionsComponent from './Components/EditEmployerContributionsComponent'
import EditUserContributionsComponent from './Components/EditUserContributionsComponent'

function App() {


  function AuthenticatedRoute({ children }: { children: React.ReactNode }){
    const isUserAuthenticated=isUserLoggedIn();
    if(isUserAuthenticated){
      return children;
    }
    return <Navigate to="/"/>
  }
  
  return (
    <>
    
      <HeaderComponent/>
      <Routes>
        <Route path="/" element={<LoginComponent/>}></Route>
        <Route path="/login" element={<LoginComponent/>}></Route>
        <Route path="/register" element={<RegisterComponent/>}></Route>
        <Route path="/getUserPlanById/:id" element={<AuthenticatedRoute><UserPlanComponent/></AuthenticatedRoute>}></Route>
        <Route path="/editUserContributions/:id" element={<AuthenticatedRoute><EditUserContributionsComponent/></AuthenticatedRoute>}></Route>
        <Route path="/editEmployerContributions/:id" element={<AuthenticatedRoute><EditEmployerContributionsComponent/></AuthenticatedRoute>}></Route>
        <Route path="/getAllUsersPlan" element={<AuthenticatedRoute><EmployerUserPlansComponent/></AuthenticatedRoute>}></Route>
      </Routes>
      <FooterComponent/>
    
    </>
  )
}

export default App
