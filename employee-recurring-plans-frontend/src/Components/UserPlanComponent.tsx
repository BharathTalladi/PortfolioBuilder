import { useState, useEffect } from 'react';
import { getUserPlanById } from '../Service/Service';
import {  useParams,useNavigate } from 'react-router-dom';
import { Table, TableCell, TableContainer, TableHead, TableRow, Button, TableBody } from '@mui/material';

interface ContributionAmounts {
    [key: string]: number;
}

interface UserData {
    name: String;
    age: number;
    salary:number;
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
                           <Table className='informationTable'>
                           <TableHead>
                                    <TableRow>
                                        <TableCell colSpan={2}>Employee Details</TableCell>
                                    </TableRow>
                                </TableHead>
                            <TableBody>
                            <TableRow className='columnHeader'>
                                        <TableCell>Name</TableCell>
                                        <TableCell>{userData.name}</TableCell>
                                    </TableRow>
                                    <TableRow className='columnHeader'>
                                        <TableCell>Age</TableCell>
                                        <TableCell>{userData.age}years</TableCell>
                                    </TableRow>
                                    <TableRow>
                                        <TableCell>Salary</TableCell>
                                        <TableCell>${userData.salary}</TableCell>
                                    </TableRow>
                                    <TableRow>
                                    <TableCell>Salary After Contributions</TableCell>
                                    <TableCell>${userData.salaryAfterContributions}</TableCell>
                                    </TableRow>
                                </TableBody>
                                </Table>
                                <Table>
                                <TableHead sx={{ marginTop:"10px" }} >
                                    <TableRow>
                                        <TableCell colSpan={4}>Self Contributions
                                        
                                        <Button className='editButton' variant="contained" sx={{ mt: 0.5, mb: 0.5 }} onClick={() => navigate(`/editUserContributions/${id}`, {
                                            state: {
                                                userPlanData: {
                                                    salary: userData.salary,
                                                    self_contribution_limit_401K: userData.selfContributionAmount.self_contribution_amount_401K,
                                                    self_contribution_limit_HSA: userData.selfContributionAmount.self_contribution_amount_HSA,
                                                    self_contribution_limit_FSA: userData.selfContributionAmount.self_contribution_amount_FSA,
                                                    self_contribution_limit_ROTHIRA: userData.selfContributionAmount.self_contribution_amount_ROTHIRA,
                                                },
                                            },
                                        })}>
                                            Update
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
                        <Button sx={{ mt:0.8, marginLeft: "12px" }} variant="contained" onClick={()=> navigate(`/createUserPlan`)}>
                           Create your Plan
                        </Button>
                        </>
                    )
            
    );
};

export default UserPlanComponent;
