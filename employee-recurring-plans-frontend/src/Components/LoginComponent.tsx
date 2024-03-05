import {useState}  from 'react';
import { useNavigate,Link,useLocation} from 'react-router-dom';
import { loginUser,saveLoggedInUser,storeToken,isAdminUser } from '../Service/Service';
import { LockOutlined } from "@mui/icons-material";
import { Container, CssBaseline,Box,Avatar, Typography,TextField,Button, Grid,} from "@mui/material";

const LoginComponent = ()=>{

    const [password,setPassword]=useState('');
    const [id,setId]=useState('');
    const [idError, setIdError] = useState(false);
    const [passwordError, setPasswordError] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const location = useLocation();
    const [displayEmployeeId ] = useState<string>(location?.state?.data?.id);
    const navigate = useNavigate();

    async function userLogin(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault();
        if(!id){
            setIdError(true);
            return
        }
        if(!password){
            setPasswordError(true);
            return;
        }
        setIdError(false);
        setPasswordError(false);
		const loginData = { id, password };
        console.log(loginData);
        await loginUser(loginData).then((response) => {
            if(response.data.role == "USER" || response.data.role=="ADMIN" ){
            const token = 'Bearer ' + response.data.token;
            const role=response.data.role;
            storeToken(token);
            saveLoggedInUser(id,role);
            if (isAdminUser()) {
                navigate('/getAllUsersPlan');
            } else {
                navigate(`/getUserPlanById/${id}`);
            }}
            else{
                setErrorMessage('Incorrect login credentials. Please try again.');
            }
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
            <TextField value={id} name='id' label="Employee ID"  error={idError} margin="normal" fullWidth autoFocus onChange={(s)=> setId(s.target.value)} required/>
            <TextField value={password} label="Password" margin="normal" error={passwordError} fullWidth autoFocus name='password'  onChange={(s)=> setPassword(s.target.value)} required/>
            {errorMessage && (
                            <Typography variant="body2" color="error" sx={{ mt: 1 }}>
                                {errorMessage}
                            </Typography>
            )}
            <Button fullWidth variant="contained" sx={{ mt: 3, mb: 2 }} onClick={(u)=> userLogin(u)}>Login</Button>
            <Grid container justifyContent={"flex-end"}>
              <Grid item>
                <Link to="/register">Don't have an account? Register</Link>
              </Grid>
            </Grid>
            </Box>
            {displayEmployeeId && (
                            <Typography variant="body1" color="InfoText" sx={{ mt: 1 }}>
                            Successfully Registered!!! Your Employee Id is <b>{displayEmployeeId}</b>
                            </Typography>
            )
            }
        </Box>
        </Container>
    </>
)
}



export default LoginComponent;