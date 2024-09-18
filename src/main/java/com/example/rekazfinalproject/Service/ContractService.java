package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.DTO.ContractDTO;
import com.example.rekazfinalproject.Model.*;
import com.example.rekazfinalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor


public class ContractService {

    private final ContractRepository contractRepository;
    private final InvestorRepository investorRepository;
    private final OwnerRepository ownerRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<Contract> getAllContract(){
        return contractRepository.findAll();
    }

    public void addContract(Integer ownerId , Integer investorId , Integer projectId , ContractDTO contractDTO){
        Investor investor = investorRepository.findInvestorById(investorId);
        if(investor==null){
            throw new ApiException("investor not found");
        }

        Owner owner = ownerRepository.findOwnerById(ownerId);
        if(owner==null){
            throw new ApiException("owner not found");
        }

        Project project = projectRepository.findProjectById(projectId);
        if(project==null){
            throw new ApiException("project not found");
        }

        User investorUser = userRepository.findUserById(investorId);
        if(investorUser.isActive()==false){
            throw new ApiException("investor is not active");
        }

        User ownerUser = userRepository.findUserById(ownerId);
        if(ownerUser.isActive()==false){
            throw new ApiException("owner is not active");
        }

        Bid bid = new Bid();

        for(Bid bid1 : investor.getBids()){
            if(bid1.getProject()==project ){
                bid=bid1 ;
            }
        }

        if(bid==null){
            throw new ApiException("bid not found");
        }

        if (bid.getStatus() != Bid.BidStatus.APPROVED) {
            throw new ApiException("Bid not approved");
        }

                // End date should be after start date
        if(contractDTO.getEndDate().isBefore(contractDTO.getStartDate()) || contractDTO.getEndDate().isEqual(contractDTO.getStartDate())){
            throw new ApiException("End date should be after start date");
        }

        Contract contract = new Contract(null, contractDTO.getTerms(),
                contractDTO.getStartDate(), contractDTO.getEndDate(),
                Contract.ContractStatus.PENDING, investor, owner, project);
        contractRepository.save(contract);

    }


    public void updateContract( Integer adminId , Integer contractId , ContractDTO contractDTO){
        User adminUser = userRepository.findUserById(adminId);
        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            throw new ApiException("Only admins can activate users");
        }
        Contract contract1 = contractRepository.findContractById(contractId);
        if(contract1==null){
            throw new ApiException("contract not found");
        }
        contract1.setEndDate(contractDTO.getEndDate());
        contract1.setStartDate(contractDTO.getStartDate());
        contract1.setTerms(contractDTO.getTerms());
        contractRepository.save(contract1);

    }

    public void deleteContract( Integer adminId , Integer contractId ){
        User adminUser = userRepository.findUserById(adminId);
        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            throw new ApiException("Only admins can activate users");
        }
        Contract contract1 = contractRepository.findContractById(contractId);
        if(contract1==null){
            throw new ApiException("contract not found");
        }
        Owner owner = ownerRepository.findOwnerById(contract1.getOwner().getId());
        owner.getContracts().remove(contract1);
        Investor investor = investorRepository.findInvestorById(contract1.getInvestor().getId());
        investor.getContracts().remove(contract1);
        Project project = projectRepository.findProjectById(contract1.getProject().getId());
        project.setContract(null);

        contractRepository.delete(contract1);
    }

    //Dana
    public void approveContract(Integer investorId, Integer contractId) {
        User investorUser = userRepository.findUserById(investorId);
        if (investorUser == null) {
            throw new ApiException("Investor not found");
        }

        Contract contract = contractRepository.findContractById(contractId);
        if (contract == null) {
            throw new ApiException("Contract not found");
        }

        if (contract.getStatus() == Contract.ContractStatus.VALID) {
            throw new ApiException("Contract is already Valid");
        } else if (contract.getStatus() == Contract.ContractStatus.EXPIRED) {
            throw new ApiException("Contract has been Expired");
        }

        if(contract.getInvestor().getId()!=investorId){
            throw new ApiException("Investor dont have authority");

        }

        contract.setStatus(Contract.ContractStatus.VALID);
  Project project = projectRepository.findProjectById(contract.getProject().getId());
        project.setStatus(Project.ProjectStatus.COMPLETED);
        contractRepository.save(contract);
    }

    //Dana
    public void rejectContract(Integer investorId, Integer contractId) {
        User investorUser = userRepository.findUserById(investorId);
        if (investorUser == null) {
            throw new ApiException("Investor not found");
        }

        Contract contract = contractRepository.findContractById(contractId);
        if (contract == null) {
            throw new ApiException("Contract not found");
        }

        if (contract.getStatus() == Contract.ContractStatus.VALID) {
            throw new ApiException("Contract is already Valid");
        } else if (contract.getStatus() == Contract.ContractStatus.EXPIRED) {
            throw new ApiException("Contract has been Expired");
        }

        if(contract.getInvestor().getId()!=investorId){
            throw new ApiException("Investor dont have authority");

        }

        Project project = contract.getProject();


        for(Bid bid1 : project.getBid()){
            if( bid1.getStatus().equals(Bid.BidStatus.APPROVED) ){
                bid1.setStatus(Bid.BidStatus.REJECTED); ;
            }
        }
        Owner owner = ownerRepository.findOwnerById(contract.getOwner().getId());
        owner.getContracts().remove(contract);
        Investor investor = investorRepository.findInvestorById(contract.getInvestor().getId());
        investor.getContracts().remove(contract);
        project.setContract(null);

        contractRepository.delete(contract);    }



    public List<Contract> getMyContractsOwner(Integer ownerId){
        List<Contract> myContracts = new ArrayList<>();
        for(Contract contract : contractRepository.findAll()){
            if(contract.getOwner().getId()==ownerId ) {
                myContracts.add(contract);
            }
        }
        if(myContracts.isEmpty()){
            throw new ApiException("No contracts found");
        }
        return myContracts;
    }

    public List<Contract> getMyContractsInvestor(Integer investorId){
        List<Contract> myContracts = new ArrayList<>();
        for(Contract contract : contractRepository.findAll()){
            if(contract.getInvestor().getId()==investorId ) {
                myContracts.add(contract);
            }
        }
        if(myContracts.isEmpty()){
            throw new ApiException("No contracts found");
        }
        return myContracts;
    }



}
