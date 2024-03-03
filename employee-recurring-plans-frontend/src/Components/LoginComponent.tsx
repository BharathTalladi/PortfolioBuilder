import {useState}  from 'react';
import { useNavigate,Link} from 'react-router-dom';
import { loginUser,saveLoggedInUser,storeToken,isAdminUser } from '../Service/Service';
import { LockOutlined } from "@mui/icons-material";
import { Container, CssBaseline,Box,Avatar, Typography,TextField,Button, Grid,} from "@mui/material";

const LoginComponent = ()=>{

    const [password,setPassword]=useState('');
    const [id,setId]=useState('');
    const navigate = useNavigate();

    async function userLogin(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault();
		const loginData = { id, password };
        await loginUser(loginData).then((response) => {
            const token = 'Bearer ' + response.data.token;
            const role=response.data.role;
            storeToken(token);
            saveLoggedInUser(id,role);
            if (isAdminUser()) {
                navigate('/getAllUsersPlan');
            } else {
                navigate(`/getUserPlanById/${id}`);
            }
            //window.location.reload(false);
        }).catch(error => {
            console.error(error);
        })
    }
return(
    <>
        <Container maxWidth="xs">
        <CssBaseline />
        <Box sx={{mt: 20,display: "flex",flexDirection: "column",alignItems: "center",}}>
            <Avatar sx={{ m: 1, bgcolor: "primary.light" }}>
            <LockOutlined />
             </Avatar>
            <Typography variant="h5">Login</Typography>
            <Box sx={{ mt: 1 }}>
            <TextField value={id} name='id' label="Employee ID" margin="normal" fullWidth autoFocus onChange={(s)=> setId(s.target.value)} required/>
            <TextField value={password} label="Password" margin="normal" fullWidth autoFocus name='password'  onChange={(s)=> setPassword(s.target.value)} required/>
            <Button fullWidth variant="contained" sx={{ mt: 3, mb: 2 }} onClick={(u)=> userLogin(u)}>Login</Button>
            <Grid container justifyContent={"flex-end"}>
              <Grid item>
                <Link to="/register">Don't have an account? Register</Link>
              </Grid>
            </Grid>
            </Box>
        </Box>
        </Container>
    </>
)
}



export default LoginComponent;