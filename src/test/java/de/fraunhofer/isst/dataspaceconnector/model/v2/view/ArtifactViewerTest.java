package de.fraunhofer.isst.dataspaceconnector.model.v2.view;

import de.fraunhofer.isst.dataspaceconnector.model.v2.Artifact;
import de.fraunhofer.isst.dataspaceconnector.model.v2.ArtifactDesc;
import de.fraunhofer.isst.dataspaceconnector.model.v2.ArtifactFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class ArtifactViewerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactViewerTest.class);

    private ArtifactViewer factory;

    @Before
    public void init() {
        factory = new ArtifactViewer();
    }

    @Test(expected = NullPointerException.class)
    public void create_null_throwNullPointerException() {
        /* ARRANGE */
        // Nothing to arrange.

        /* ACT && ASSERT*/
        factory.create(null);
    }

    @Test
    public void create_validDesc_validView() {
        final var artifact = getLocalArtifact();

        final var rndValue = new Random(123456).nextInt(10);
        LOGGER.info("Testing with {} artifact increments.", rndValue);
        for(int i = 0; i < rndValue; i++)
            artifact.incrementAccessCounter();

        final var view = factory.create(artifact);

        Assert.assertNotNull(view);
        Assert.assertEquals(view.getTitle(), artifact.getTitle());
        Assert.assertEquals(view.getNumAccessed(), artifact.getNumAccessed());
    }


    Artifact getLocalArtifact() {
        final var artifactFactory = new ArtifactFactory();

        final var desc = new ArtifactDesc();
        desc.setTitle("Some Title");
        desc.setValue("Value");

        return artifactFactory.create(desc);
    }
}
