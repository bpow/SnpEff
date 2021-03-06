package org.snpeff.snpEffect.testCases.unity;

import junit.framework.Assert;

import org.junit.Test;
import org.snpeff.interval.Variant;
import org.snpeff.snpEffect.HgvsDna;
import org.snpeff.snpEffect.HgvsProtein;
import org.snpeff.snpEffect.VariantEffect;
import org.snpeff.snpEffect.VariantEffects;
import org.snpeff.util.Gpr;

/**
 *
 * Test case
 */
public class TestCasesHgvsProtDup extends TestCasesBase {

	public TestCasesHgvsProtDup() {
		super();
	}

	/**
	 * Test case: p.Gly4_Gln6dup in the sequence MKMGHQQQCC denotes a duplication
	 * of amino acids Glycine-4 (Gly, G) to Glutamine-6 (Gln, Q) (i.e. MKMGHQGHQQQCC)
	 *
	 * Reference: http://www.hgvs.org/mutnomen/recs-prot.html#dup
	 */
	@Test
	public void test_01() {
		Gpr.debug("Test");

		prependSequenceToFirstExon("atgaaaatgggccatcagcagcagtgctgc"); // This is 'MKMGHQQQCC' as a DNA sequence

		if (verbose) Gpr.debug(transcript);

		// Create variant
		Variant variant = new Variant(chromosome, 898, "", "ggccatcag", ""); // Add 'GHQ' amino acids
		if (verbose) Gpr.debug("Variant: " + variant);

		// Analyze variant
		VariantEffects effs = snpEffectPredictor.variantEffect(variant);

		// Calculate HGVS
		VariantEffect eff = effs.get();
		HgvsDna hgvsc = new HgvsDna(eff);
		String hgvsDna = hgvsc.toString();
		HgvsProtein hgvsp = new HgvsProtein(eff);
		String hgvsProt = hgvsp.toString();

		// Check result
		if (verbose) {
			Gpr.debug("Eff        : " + eff);
			Gpr.debug("HGVS (DNA) : '" + hgvsDna + "'");
			Gpr.debug("HGVS (Prot): '" + hgvsProt + "'");
		}

		Assert.assertEquals("c.10_18dupGGCCATCAG", hgvsDna);
		Assert.assertEquals("p.Gly4_Gln6dup", hgvsProt);
	}

	/**
	 * Reference: http://www.hgvs.org/mutnomen/recs-prot.html#dup
	 * duplicating insertions in single amino acid stretches (or short tandem repeats) are
	 * described as a duplication, e.g. a duplicating HQ insertion in the HQ-tandem repeat
	 * sequence of MKMGHQHQCC to MKMGHQHQHQCC is described as p.His7_Gln8dup (not p.Gln8_Cys9insHisGln)
	 */
	@Test
	public void test_02() {
		Gpr.debug("Test");

		prependSequenceToFirstExon("atgaaaatgggccatcagcatcagcagcagtgctgc"); // This is 'MKMGHQQQCC' as a DNA sequence

		if (verbose) Gpr.debug(transcript);

		// Create variant
		Variant variant = new Variant(chromosome, 904, "", "catcag", ""); // Add 'HQ' amino acids
		if (verbose) Gpr.debug("Variant: " + variant);

		// Analyze variant
		VariantEffects effs = snpEffectPredictor.variantEffect(variant);

		// Calculate HGVS
		VariantEffect eff = effs.get();
		HgvsDna hgvsc = new HgvsDna(eff);
		String hgvsDna = hgvsc.toString();
		HgvsProtein hgvsp = new HgvsProtein(eff);
		String hgvsProt = hgvsp.toString();

		// Check result
		if (verbose) {
			Gpr.debug("Eff        : " + eff);
			Gpr.debug("HGVS (DNA) : '" + hgvsDna + "'");
			Gpr.debug("HGVS (Prot): '" + hgvsProt + "'");
		}

		Assert.assertEquals("c.19_24dupCATCAG", hgvsDna);
		Assert.assertEquals("p.His7_Gln8dup", hgvsProt);
	}
}
