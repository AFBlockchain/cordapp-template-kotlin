package hk.edu.polyu.af.bc.template.states

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StateTests {
    @Test
    fun hasFieldOfCorrectType() {
        // Does the field exist?
        TemplateState::class.java.getDeclaredField("msg")
        // Is the field of the correct type?
        assertEquals(TemplateState::class.java.getDeclaredField("msg").type, String()::class.java)
    }
}