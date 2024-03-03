import { useState, useEffect } from 'react';
import { getUserPlanById } from '../Service/Service';
import { NavLink, useParams,useNavigate } from 'react-router-dom';
import { Table, TableCell, TableContainer, TableHead, TableRow, Button, TableBody } from '@mui/material';

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

const UserPlanComponent = () => {
    const { id } = useParams<{ id?: string }>();
    const [userData, setUserData] = useState<UserData | null>(null);
    const navigate=useNavigate()

    useEffect(() => {
        const fetchData = async () => {
            try {
                if (id) {
                    const response = await getUserPlanById(id);
                    response?.data?.errorMeesage? navigate('/createUserPlan') : setUserData(response.data);
                }
            } catch (error) {
                console.error('Error fetching user plan:', error);
            }
        };

        if (id) {
            fetchData();
        }
    }, [id,navigate]);

    return ( userData?.selfContributionAmount ? (
                <TableContainer className='TableContainer'>
                           <Table>
                                <TableHead sx={{ marginTop:"10px" }} >
                                    <TableRow>
                                        <TableCell colSpan={3}>Self Contributions</TableCell>
                                    
                                        <TableCell colSpan={1} className='editbutton'>
                                            <Button sx={{ marginLeft: "10px", backgroundColor: "#FFF" }} variant="contained">
                                                <NavLink to={`/editUserContributions/${id}`}>Edit Self Contributions</NavLink>
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                    
                                </TableHead>
                                <TableBody>
                                <TableRow className='columnHeader'>
                                        <TableCell>Self 401k Contribution</TableCell>
                                        <TableCell>Self HSA Contribution</TableCell>
                                        <TableCell>Self FSA Contribution</TableCell>
                                        <TableCell>Self ROTH IRA Contribution</TableCell>
                                    </TableRow>
                                <TableRow>
                                    <TableCell>${userData.selfContributionAmount.self_contribution_amount_401K}</TableCell>
                                    <TableCell>${userData.selfContributionAmount.self_contribution_amount_HSA}</TableCell>
                                    <TableCell>${userData.selfContributionAmount.self_contribution_amount_FSA}</TableCell>
                                    <TableCell>${userData.selfContributionAmount.self_contribution_amount_ROTHIRA}</TableCell>
                                </TableRow>
                                </TableBody>
                            </Table>

                            {userData?.employerContributionAmount?.employer_contribution_amount_401k? (
                                <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell colSpan={4}>Employer Contributions</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                <TableRow className='columnHeader'>
                                        <TableCell>Employer 401k Contribution</TableCell>
                                        <TableCell>Employer HSA Contribution</TableCell>
                                        <TableCell>Employer FSA Contribution</TableCell>
                                        <TableCell>Employer ROTH IRA Contribution</TableCell>
                                    </TableRow>
                                <TableRow>
                                    <TableCell>${userData.employerContributionAmount.employer_contribution_amount_401k}</TableCell>
                                    <TableCell>${userData.employerContributionAmount.employer_contribution_amount_HSA}</TableCell>
                                    <TableCell>${userData.employerContributionAmount.employer_contribution_amount_FSA}</TableCell>
                                    <TableCell>${userData.employerContributionAmount.employer_contribution_amount_ROTHIRA}</TableCell>
                                </TableRow>
                                </TableBody>
                                </Table>
                            ):(
                                <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell colSpan={4}>Please contact Employer for Employer Contributions</TableCell>
                                    </TableRow>
                                </TableHead>
                                </Table>
                            )}


                            {userData?.totalContributionAmount?.total_contribution_amount_401k? (
                                <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell colSpan={4}>Total Contributions</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                <TableRow className='columnHeader'>
                                        <TableCell>Total 401k Contribution</TableCell>
                                        <TableCell>Total HSA Contribution</TableCell>
                                        <TableCell>Total FSA Contribution</TableCell>
                                        <TableCell>Total ROTH IRA Contribution</TableCell>
                                    </TableRow>
                                <TableRow>
                                    <TableCell>${userData.totalContributionAmount.total_contribution_amount_401k}</TableCell>
                                    <TableCell>${userData.totalContributionAmount.total_contribution_amount_HSA}</TableCell>
                                    <TableCell>${userData.totalContributionAmount.total_contribution_amount_FSA}</TableCell>
                                    <TableCell>${userData.totalContributionAmount.total_contribution_amount_ROTHIRA}</TableCell>
                                </TableRow>
                                </TableBody>
                            </Table>

                            ):(
                                <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell colSpan={4}>After Employer contributes, total Contribution Amount will be calculated. Please reach out to Employer</TableCell>
                                    </TableRow>
                                </TableHead>
                                </Table>
                            )}

                            


                    </TableContainer>
                    ) : (
                        <>
                        <br/><br/><br/><br/><br/><br/><br/><br/><br/>
                        <Button sx={{ marginLeft: "10px", backgroundColor: "#FFF" }} variant="contained">
                            <NavLink to="/createUserPlan">Create User Plan</NavLink>
                        </Button>
                        </>
                    )
                
    );
};

export default UserPlanComponent;
