import {NavLink, useNavigate } from 'react-router-dom';
import { isUserLoggedIn, logoutUser } from '../Service/Service';
import { AppBar, Button,Toolbar,Typography} from "@mui/material";
import React from "react";

const HeaderComponent = () => {

    const isUserAuthenticated = isUserLoggedIn();
    const navigate=useNavigate();
    function logout(){
        logoutUser();
        navigate('/login');
    }

    return (
        <React.Fragment>
        <AppBar >
          <Toolbar>
                <div className='header-left'>
                    <Typography sx={{ fontSize: "1.5rem" }}>
                        Portfolio Builder
                    </Typography>
                </div>
                <div className='header-right' style={{ marginLeft: 'auto' }}>
                {!isUserAuthenticated && <Button sx={{ marginLeft: "auto",backgroundColor: "#FFF" }} variant="contained">
                <NavLink to="/register">Sign Up</NavLink>
                </Button>}
                {!isUserAuthenticated && <Button sx={{ marginLeft: "10px",backgroundColor: "#FFF" }} variant="contained">
                <NavLink to="/login">Login</NavLink>
                </Button>}
                { isUserAuthenticated && <Button sx={{ marginLeft: "10px",backgroundColor: "#FFF" }} variant="contained">
                <NavLink to="/login"  onClick={logout}>Logout</NavLink>
                </Button>}
                </div>         
          </Toolbar>
        </AppBar>
      </React.Fragment>
    );
};
  
export default HeaderComponent;