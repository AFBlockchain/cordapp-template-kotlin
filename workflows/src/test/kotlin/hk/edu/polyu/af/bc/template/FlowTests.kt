package hk.edu.polyu.af.bc.template

import hk.edu.polyu.af.bc.template.flows.Initiator
import hk.edu.polyu.af.bc.template.states.TemplateState
import net.corda.core.node.services.Vault.StateStatus
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.transactions.SignedTransaction
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.MockNetworkParameters
import net.corda.testing.node.StartedMockNode
import net.corda.testing.node.TestCordapp
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.concurrent.Future

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlowTests {
    private lateinit var network: MockNetwork
    private lateinit var a: StartedMockNode
    private lateinit var b: StartedMockNode

    @BeforeAll
    fun setup() {
        network = MockNetwork(
            MockNetworkParameters(
                cordappsForAllNodes = listOf(
                    TestCordapp.findCordapp("hk.edu.polyu.af.bc.template.contracts"),
                    TestCordapp.findCordapp("hk.edu.polyu.af.bc.template.flows")
                )
            )
        )
        a = network.createPartyNode()
        b = network.createPartyNode()
        network.runNetwork()
    }

    @AfterAll
    fun tearDown() {
        network.stopNodes()
    }
    @Test
    fun dummyTest() {
        val flow = Initiator(b.info.legalIdentities[0])
        val future: Future<SignedTransaction> = a.startFlow(flow)
        network.runNetwork()

        // successful query means the state is stored at node b's vault. Flow went through.
        val inputCriteria: QueryCriteria = QueryCriteria.VaultQueryCriteria().withStatus(StateStatus.UNCONSUMED)
        val state = b.services.vaultService.queryBy(TemplateState::class.java, inputCriteria).states[0].state.data
    }
}
