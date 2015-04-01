package org.haw.lnielsen.gka.graphen.io.loader;

import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_GKA;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_I;

public class GraphParser_GKATest extends GraphParserTest {
	@Override
	protected GraphParser_I createParser() {
		return new GraphParser_GKA();
	}
}
