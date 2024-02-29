import { useState, useEffect } from 'react';
import {getUserPlanById} from '../Service/Service';
import { NavLink,useParams } from 'react-router-dom';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button } from '@mui/material';


interface ContributionAmounts {
    [key: string]: number;
}

interface UserData {
    age: number;
    salaryAfterContributions: number;
    selfContributionAmount: ContributionAmounts;
    employerContributionAmount: ContributionAmounts;
    totalContributionAmount: ContributionAmounts;
}

const UserPlanComponent = ()=>{
    const {id} = useParams<{id?:string}>();
    
    const [userData, setUserData] = useState<UserData | null>(null);
    useEffect(() => {
        const fetchData = async () => {
            try {
                if(id){
                    
                    const response = await getUserPlanById(id);
                    console.log(response)
                    console.log(id);
                    setUserData(response.data);
                }
            } catch (error) {
                console.error('Error fetching user plan:', error);
            }
        };
        fetchData();
    }, [id]);

    return(
        <>
            {userData && (
                <div>
                    <h2>User Plan Details</h2>
                    <TableContainer component={Paper}>
                        <Table>
                           <TableHead>
                            Self Contribution Amount
                            <Button sx={{ marginLeft: "10px",backgroundColor: "#FFF" }} variant="contained">
                            <NavLink to="/editUserContributions/${id}">Edit Self COntributions</NavLink>
                            </Button>
                           </TableHead>
                           <TableRow>
                                <TableCell>Self 401k Contribution</TableCell>
                                <TableCell>Self HSA Contribution </TableCell>
                                <TableCell>Self FSA Contribution </TableCell>
                                <TableCell>Self ROTH IRA Contribution </TableCell>
                           </TableRow>
                           <TableRow>
                                    <TableCell>{userData.selfContributionAmount.self_contribution_amount_401K}</TableCell>
                                    <TableCell>{userData.selfContributionAmount.self_contribution_amount_HSA}</TableCell>
                                    <TableCell>{userData.selfContributionAmount.self_contribution_amount_FSA}</TableCell>
                                    <TableCell>{userData.selfContributionAmount.self_contribution_amount_ROTHIRA}</TableCell>
                           </TableRow>
                           </Table>
                           <Table>
                           <TableHead>Employer Contribution Amount</TableHead>
                           <TableRow>
                                <TableCell>Employer 401k Contribution </TableCell>
                                <TableCell>Employer HSA Contribution</TableCell>
                                <TableCell>Employer FSA Contribution</TableCell>
                                <TableCell>Employer ROTH IRA Contribution</TableCell>
                           </TableRow>
                           <TableRow>
                                    <TableCell>{userData.employerContributionAmount.employer_contribution_amount_401k}</TableCell>
                                    <TableCell>{userData.employerContributionAmount.employer_contribution_amount_HSA}</TableCell>
                                    <TableCell>{userData.employerContributionAmount.employer_contribution_amount_FSA}</TableCell>
                                    <TableCell>{userData.employerContributionAmount.employer_contribution_amount_ROTHIRA}</TableCell>
                           </TableRow>

                           </Table>
                           <Table>
                           <TableHead>Total Contributions Amount</TableHead>
                           <TableRow>
                                <TableCell>Total 401k Contribution</TableCell>
                                <TableCell>Total HSA Contribution</TableCell>
                                <TableCell>Total FSA Contribution</TableCell>
                                <TableCell>Total ROTH IRA Contribution</TableCell>
                           </TableRow>
                           <TableRow>
                                    <TableCell>{userData.totalContributionAmount.total_contribution_amount_401k}</TableCell>
                                    <TableCell>{userData.totalContributionAmount.total_contribution_amount_HSA}</TableCell>
                                    <TableCell>{userData.totalContributionAmount.total_contribution_amount_FSA}</TableCell>
                                    <TableCell>{userData.totalContributionAmount.total_contribution_amount_ROTHIRA}</TableCell>
                                </TableRow>

                        </Table>
                    </TableContainer>
                </div>
            )}
        </>
    )
}

export default UserPlanComponent;