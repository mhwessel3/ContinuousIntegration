package ci;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCloneBuild {
    BuildAndTest bat = new BuildAndTest();

    @Test
    public void testCloneBranch() {
        String ret = bat.cloneBranch();
        assertFalse(ret.equalsIgnoreCase("Folder clone failed."));
    }

    @Test
    public void testBuildBranch() {
        String ret = bat.buildBranch();
        assertFalse(ret.equalsIgnoreCase("Build Failed"));
    }
}
