import {useState}  from 'react';
import { useNavigate, Link} from 'react-router-dom';
import {registerUser} from '../Service/Service';
import { LockOutlined } from "@mui/icons-material";
import { Container, CssBaseline,Box,Avatar, Typography,TextField,Button, Grid,} from "@mui/material";

const RegisterComponent = ()=>{
    const [name, setName] = useState('')
    const [emailId, setEmailId] = useState('')
    const [password,setPassword] = useState('')
    const [nameError, setNameError] = useState(false);
    const [emailIdError, setEmailIdError] = useState(false);
    const [passwordError, setPasswordError] = useState(false);
    const navigate = useNavigate();

    const userRegister = (u: React.MouseEvent<HTMLButtonElement>) =>{
        u.preventDefault();
        if(!name){
            setNameError(true);
            return
        }
        if(!emailId){
            setEmailIdError(true);
            return;
        }
        if(!password){
            setPasswordError(true);
            return;
        }
        setNameError(false);
        setEmailIdError(false);
        setPasswordError(false);
        const user={name,emailId,password};
        console.log(user);
        registerUser(user).then((response)=> {
            console.log(response)
            const data=response.data;
            navigate('/login', { state: { data } })
        }).catch(error => {
            console.log(error)
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
            <Typography variant="h5">Sign up</Typography>
            <Box sx={{ mt: 3 }}>
            <Grid container spacing={2}>
            <Grid item xs={12}>
                <TextField value={name} name='name' label="Name" id="name" fullWidth autoFocus onChange={(s)=> setName(s.target.value)} required error={nameError}/>
            </Grid>
            <Grid item xs={12}>
                <TextField value={emailId} name='emailId' label="Email Address" id="emailId" fullWidth autoFocus onChange={(s)=> setEmailId(s.target.value)} required error={emailIdError}/>
            </Grid>
            <Grid item xs={12}>
                <TextField value={password} name='password' label="Password" id="password" fullWidth autoFocus onChange={(s)=> setPassword(s.target.value)} required error={passwordError}/>
            </Grid>
            </Grid>
            <Button fullWidth variant="contained" sx={{ mt: 3, mb: 2 }} onClick={(u)=> userRegister(u)}>Register</Button>
            <Grid container justifyContent={"flex-end"}>
              <Grid item>
                <Link to="/login">Already have an account? Login</Link>
              </Grid>
            </Grid>
            </Box>
        </Box>
        </Container>
        </>
    )
}

export default RegisterComponent;