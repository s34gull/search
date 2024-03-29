package org.butterfish.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.maana.search.Search;
import com.maana.search.SearchResult;

/**
 * 
 * @author jedwards
 *
 */
public class SearchTest {
	private static final String gettysburgAddress = "Four score and seven years ago our fathers brought forth on this continent, a new nation, conceived in Liberty, and dedicated to the proposition that all men are created equal. Now we are engaged in a great civil war, testing whether that nation, or any nation so conceived and so dedicated, can long endure. We are met on a great battle-field of that war. We have come to dedicate a portion of that field, as a final resting place for those who here gave their lives that that nation might live. It is altogether fitting and proper that we should do this. But, in a larger sense, we can not dedicate -- we can not consecrate -- we can not hallow -- this ground. The brave men, living and dead, who struggled here, have consecrated it, far above our poor power to add or detract. The world will little note, nor long remember what we say here, but it can never forget what they did here. It is for us the living, rather, to be dedicated here to the unfinished work which they who fought here have thus far so nobly advanced. It is rather for us to be here dedicated to the great task remaining before us -- that from these honored dead we take increased devotion to that cause for which they gave the last full measure of devotion -- that we here highly resolve that these dead shall not have died in vain -- that this nation, under God, shall have a new birth of freedom -- and that government of the people, by the people, for the people, shall not perish from the earth.";
	private static final String hamletsSoliloquy = "To be, or not to be -- that is the question: Whether 'tis nobler in the mind to suffer The slings and arrows of outrageous fortune Or to take arms against a sea of troubles And by opposing end them. To die, to sleep -- No more -- and by a sleep to say we end The heartache, and the thousand natural shocks That flesh is heir to. 'Tis a consummation Devoutly to be wished. To die, to sleep-- To sleep--perchance to dream: ay, there's the rub, For in that sleep of death what dreams may come When we have shuffled off this mortal coil, Must give us pause. There's the respect That makes calamity of so long life. For who would bear the whips and scorns of time, Th' oppressor's wrong, the proud man's contumely The pangs of despised love, the law's delay, The insolence of office, and the spurns That patient merit of th' unworthy takes, When he himself might his quietus make With a bare bodkin? Who would fardels bear, To grunt and sweat under a weary life, But that the dread of something after death, The undiscovered country, from whose bourn No traveller returns, puzzles the will, And makes us rather bear those ills we have Than fly to others that we know not of? Thus conscience does make cowards of us all, And thus the native hue of resolution Is sicklied o'er with the pale cast of thought, And enterprise of great pitch and moment With this regard their currents turn awry And lose the name of action. -- Soft you now, The fair Ophelia! -- Nymph, in thy orisons Be all my sins remembered.";
	private static final String pantagruel = "I must refer you to the great chronicle of Pantagruel for the knowledge of that genealogy and antiquity of race by which Gargantua is come unto us. In it you may understand more at large how the giants were born in this world, and how from them by a direct line issued Gargantua, the father of Pantagruel: and do not take it ill, if for this time I pass by it, although the subject be such, that the oftener it were remembered, the more it would please your worshipful Seniorias; according to which you have the authority of Plato in Philebo and Gorgias; and of Flaccus, who says that there are some kinds of purposes (such as these are without doubt), which, the frequentlier they be repeated, still prove the more delectable. Would to God everyone had as certain knowledge of his genealogy since the time of the ark of Noah until this age. I think many are at this day emperors, kings, dukes, princes, and popes on the earth, whose extraction is from some porters and pardon-pedlars; as, on the contrary, many are now poor wandering beggars, wretched and miserable, who are descended of the blood and lineage of great kings and emperors, occasioned, as I conceive it, by the transport and revolution of kingdoms and empires, from the Assyrians to the Medes, from the Medes to the Persians, from the Persians to the Macedonians, from the Macedonians to the Romans, from the Romans to the Greeks, from the Greeks to the French. And to give you some hint concerning myself, who speaks unto you, I cannot think but I am come of the race of some rich king or prince in former times; for never yet saw you any man that had a greater desire to be a king, and to be rich, than I have, and that only that I may make good cheer, do nothing, nor care for anything, and plentifully enrich my friends, and all honest and learned men. But herein do I comfort myself, that in the other world I shall be so, yea and greater too than at this present I dare wish. As for you, with the same or a better conceit consolate yourselves in your distresses, and drink fresh if you can come by it. To return to our wethers, I say that by the sovereign gift of heaven, the antiquity and genealogy of Gargantua hath been reserved for our use more full and perfect than any other except that of the Messias, whereof I mean not to speak; for it belongs not unto my purpose, and the devils, that is to say, the false accusers and dissembled gospellers, will therein oppose me. This genealogy was found by John Andrew in a meadow, which he had near the pole-arch, under the olive-tree, as you go to Narsay: where, as he was making cast up some ditches, the diggers with their mattocks struck against a great brazen tomb, and unmeasurably long, for they could never find the end thereof, by reason that it entered too far within the sluices of Vienne. Opening this tomb in a certain place thereof, sealed on the top with the mark of a goblet, about which was written in Etrurian letters Hic Bibitur, they found nine flagons set in such order as they use to rank their kyles in Gascony, of which that which was placed in the middle had under it a big, fat, great, grey, pretty, small, mouldy, little pamphlet, smelling stronger, but no better than roses. In that book the said genealogy was found written all at length, in a chancery hand, not in paper, not in parchment, nor in wax, but in the bark of an elm-tree, yet so worn with the long tract of time, that hardly could three letters together be there perfectly discerned. I (though unworthy) was sent for thither, and with much help of those spectacles, whereby the art of reading dim writings, and letters that do not clearly appear to the sight, is practised, as Aristotle teacheth it, did translate the book as you may see in your Pantagruelizing, that is to say, in drinking stiffly to your own heart's desire, and reading the dreadful and horrific acts of Pantagruel. At the end of the book there was a little treatise entitled the Antidoted Fanfreluches, or a Galimatia of extravagant conceits. The rats and moths, or (that I may not lie) other wicked beasts, had nibbled off the beginning: the rest I have hereto subjoined, for the reverence I bear to antiquity.";

	private static final List<Long> expected_positions = Arrays.asList(15L, 42L, 45L, 88L, 249L);

	private Search search = null;

	@Before
	public void setUp() {
		search = new HashSearch();
	}

	@Test
	public void testSingleIndexOneTermSingleOccurrence() {
		long start = System.nanoTime();
		search.indexDocument("Gettysburg Address", gettysburgAddress);
		long stop = System.nanoTime();

		List<SearchResult> results = search.search("score");

		// Ensure we got results
		assertNotNull(results);

		// We should only have a single result
		assertEquals(1, results.size());

		// The name of the document in the result should be 'Gettysburg Address'
		assertEquals(results.get(0).getDocumentName(), "Gettysburg Address");
		assertEquals(results.get(0).getWeightedScore(), 21);

		// Results should only have found this in one position
		assertEquals(results.get(0).getDocumentPositions().size(), 1);

		// That position is at index=1 (first word is index=0)
		assertEquals(results.get(0).getDocumentPositions().first().getPositions().size(), 1);

		// That position is at index=1 (first word is index=0)
		assertEquals((long) results.get(0).getDocumentPositions().first().getPositions().get(0), 1L);
	}

	@Test
	public void testSingleIndexSingleTermMultipleOccurrence() {
		long start = System.nanoTime();
		search.indexDocument("Gettysburg Address", gettysburgAddress);
		long stop = System.nanoTime();

		List<SearchResult> results = search.search("nation");

		// Ensure we got results
		assertNotNull(results);

		// We should only have a single result
		assertEquals(1, results.size());

		// The name of the document in the result should be 'Gettysburg Address'
		assertEquals(results.get(0).getDocumentName(), "Gettysburg Address");
		assertEquals(results.get(0).getWeightedScore(), 54);

		// Search results should only contain hit for single term
		assertEquals(results.get(0).getDocumentPositions().size(), 1);

		// There were 5 occurrences of this word
		assertEquals(results.get(0).getDocumentPositions().first().getPositions().size(), 5);

		// That position is at index=15 (first word is index=0)
		assertEquals((long) results.get(0).getDocumentPositions().first().getPositions().get(0), 15L);
	}

	@Test
	public void testSingleIndexMultiTermMultipleOccurrence() {
		long start = System.nanoTime();
		search.indexDocument("Gettysburg Address", gettysburgAddress);
		long stop = System.nanoTime();

		List<SearchResult> results = search.search("score nation");

		// Ensure we got results
		assertNotNull(results);

		// We should only have a single result document
		assertEquals(1, results.size());

		// The name of the document in the result should be 'Gettysburg Address'
		assertEquals(results.get(0).getDocumentName(), "Gettysburg Address");
		assertEquals(results.get(0).getWeightedScore(), 118);

		// Search results should only contain hit for two terms
		assertEquals(results.get(0).getDocumentPositions().size(), 2);

		// "nation" should appear first in descending set
		// "score" should appear second (last) - based on internal score
		assertEquals(results.get(0).getDocumentPositions().descendingSet().first().getWord(), "nation");
		assertEquals(results.get(0).getDocumentPositions().descendingSet().last().getWord(), "score");

		// validate that number of hits are correct
		assertEquals(results.get(0).getDocumentPositions().descendingSet().first().getPositions().size(), 5);
		assertEquals(results.get(0).getDocumentPositions().descendingSet().last().getPositions().size(), 1);

		// check positions for "nation" - positions are rooted at zero
		List<Long> found_positions = results.get(0).getDocumentPositions().descendingSet().first().getPositions();
		for (long position : found_positions) {
			assertEquals(expected_positions.indexOf(position), found_positions.indexOf(position));
		}

		// check position for "score"
		assertEquals((long) results.get(0).getDocumentPositions().descendingSet().last().getPositions().get(0), 1);
	}

	@Test
	public void testMultiIndexSingleTermSingleOccurrence() {
		long start = System.nanoTime();
		search.indexDocument("Gettysburg Address", gettysburgAddress);
		long stop = System.nanoTime();

		start = System.nanoTime();
		search.indexDocument("Hamlet's Soliloquy", hamletsSoliloquy);
		stop = System.nanoTime();

		List<SearchResult> results = search.search("score");

		// Ensure we got results
		assertNotNull(results);

		// We should only have a single result ("score" is not present in
		// Hamlet's Soliloquy)
		assertEquals(1, results.size());

		// The name of the document in the result should be 'Gettysburg Address'
		assertEquals(results.get(0).getDocumentName(), "Gettysburg Address");
		assertEquals(results.get(0).getWeightedScore(), 21);

		// Results should only have found this in one position
		assertEquals(results.get(0).getDocumentPositions().size(), 1);

		// That position is at index=1 (first word is index=0)
		assertEquals(results.get(0).getDocumentPositions().first().getPositions().size(), 1);

		// That position is at index=1 (first word is index=0)
		assertEquals((long) results.get(0).getDocumentPositions().first().getPositions().get(0), 1L);
	}

	@Test
	public void testMultiIndexMultiTermSingleOccurrence() {
		long start = System.nanoTime();
		search.indexDocument("Gettysburg Address", gettysburgAddress);
		long stop = System.nanoTime();

		start = System.nanoTime();
		search.indexDocument("Hamlet's Soliloquy", hamletsSoliloquy);
		stop = System.nanoTime();

		List<SearchResult> results = search.search("score question");

		// Ensure we got results
		assertNotNull(results);

		// We should only have a single result ("score" is not present in
		// Hamlet's Soliloquy; "question" is not in the Gettysburg Address)
		assertEquals(2, results.size());

		// The name of the document in the result should be
		// 'Hamlet's Soliloquy' then 'Gettysburg Address'
		// as 'question' comes before 'score' in the results array
		// since both words have the same score, lexical sort order is
		// applied
		assertEquals(results.get(0).getDocumentName(), "Hamlet's Soliloquy");
		assertEquals(results.get(0).getWeightedScore(), 21);

		assertEquals(results.get(1).getDocumentName(), "Gettysburg Address");
		assertEquals(results.get(0).getWeightedScore(), 21);

		// Each term maps to one document which, which has only one word in its
		// result list
		assertEquals(results.get(0).getDocumentPositions().size(), 1);
		assertEquals(results.get(1).getDocumentPositions().size(), 1);

		// That document's word only occurs once
		assertEquals(results.get(0).getDocumentPositions().first().getPositions().size(), 1);
		assertEquals(results.get(1).getDocumentPositions().first().getPositions().size(), 1);

		// Hamlet utters 'question' at offset 10
		assertEquals((long) results.get(0).getDocumentPositions().first().getPositions().get(0), 10L);

		// Lincoln utters 'score' at offset 1
		assertEquals((long) results.get(1).getDocumentPositions().first().getPositions().get(0), 1L);
	}

	@Test
	public void testMultiIndexMultiTermMultiOccurrence() {
		long start = System.nanoTime();
		search.indexDocument("Gettysburg Address", gettysburgAddress);
		long stop = System.nanoTime();

		start = System.nanoTime();
		search.indexDocument("Hamlet's Soliloquy", hamletsSoliloquy);
		stop = System.nanoTime();

		List<SearchResult> results = search.search("nation question");

		// Ensure we got results
		assertNotNull(results);

		// We should only have a single result ("score" is not present in
		// Hamlet's Soliloquy; "question" is not in the Gettysburg Address)
		assertEquals(2, results.size());

		// The name of the document in the result should be
		// 'Gettysburg Address' then 'Hamlet's Soliloquy'
		// as 'nation' gives Lincoln's speech a higher score
		assertEquals(results.get(0).getDocumentName(), "Gettysburg Address");
		assertEquals(results.get(0).getWeightedScore(), 54);

		assertEquals(results.get(1).getDocumentName(), "Hamlet's Soliloquy");
		assertEquals(results.get(1).getWeightedScore(), 21);

		// Each term maps to one document which, which has only one word in its
		// result list
		assertEquals(results.get(0).getDocumentPositions().size(), 1);
		assertEquals(results.get(1).getDocumentPositions().size(), 1);

		// That document's word only occurs once
		assertEquals(results.get(0).getDocumentPositions().first().getPositions().size(), 5);
		assertEquals(results.get(1).getDocumentPositions().first().getPositions().size(), 1);

		// Hamlet utters 'question' at offset 10
		assertEquals((long) results.get(1).getDocumentPositions().first().getPositions().get(0), 10L);

		// check positions for "nation" - positions are rooted at zero
		List<Long> found_positions = results.get(0).getDocumentPositions().descendingSet().first().getPositions();
		for (long position : found_positions) {
			assertEquals(expected_positions.indexOf(position), found_positions.indexOf(position));
		}
	}

	@Test
	public void testMultiIndexMultiTermZeroOccurrence() {
		long start = System.nanoTime();
		search.indexDocument("Gettysburg Address", gettysburgAddress);
		long stop = System.nanoTime();

		start = System.nanoTime();
		search.indexDocument("Hamlet's Soliloquy", hamletsSoliloquy);
		stop = System.nanoTime();

		List<SearchResult> results = search.search("BADBEEF");
		
		assertNotNull(results);
		
		assertTrue(results.isEmpty());
	}
	
	/**
	 * Run as Application to get basic benchmarks for indexing and searching.
	 * Indexing takes ~5ms per "document"; search, once warmed, takes ~500 us
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {
			Search search = new HashSearch();

			long start = System.nanoTime();
			search.indexDocument("Gettysburg Address", gettysburgAddress);
			long stop = System.nanoTime();
			System.out.printf("Searcher: Indexed Gettysburg Address in %.4f us.\n", ((double) (stop - start) / 1000));

			start = System.nanoTime();
			search.indexDocument("Hamlet's Soliloquy", hamletsSoliloquy);
			stop = System.nanoTime();
			System.out.printf("Searcher: Indexed Hamlet's Soliloquy in %.4f us.\n", ((double) (stop - start) / 1000));

			start = System.nanoTime();
			search.indexDocument("Pantagruel", pantagruel);
			stop = System.nanoTime();
			System.out.printf("Searcher: Indexed Pantagruel in %.4f us.\n", ((double) (stop - start) / 1000));

			start = System.nanoTime();
			List<SearchResult> results = search.search("nation nobler died die altogether Pantagruel father Gargantua");
			stop = System.nanoTime();
			System.out.printf("Searcher: Searched corpus in %.4f us.\n", ((double) (stop - start) / 1000));

			for (SearchResult result : results) {
				System.out.printf("Searcher: result => %s\n", result);
			}

			start = System.nanoTime();
			results = search.search("man men war arrow happiness");
			stop = System.nanoTime();
			System.out.printf("Searcher: Searched corpus in %.4f us.\n", ((double) (stop - start) / 1000));

			for (SearchResult result : results) {
				System.out.printf("Searcher: result => %s\n", result);
			}
		}
	}

}
