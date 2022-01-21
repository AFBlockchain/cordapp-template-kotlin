package hk.edu.polyu.af.bc.template.states

import hk.edu.polyu.af.bc.template.contracts.TemplateContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party

@BelongsToContract(TemplateContract::class)
data class TemplateState(
    val msg: String,
    val sender: Party,
    val receiver: Party,
    override val participants: List<AbstractParty> = listOf(sender, receiver)
) : ContractState
