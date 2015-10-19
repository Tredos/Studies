package junit.bmiPerson;

public enum Corpulence {
	MALNUTRITION {
		@Override
		public String toString() {
			return "dénutrition ou famine";
		}
		@Override
		public String getAdvice(){
			return "Cette maigreur excessive peut être la conséquence de maladies, "
					+ " de troubles psychologiques, "
					+ "et elle peut aussi être elle-même à l’origine d’autres maladies "
					+ "entraînant ainsi un cercle vicieux. Il est utile que vous consultiez "
					+ "un médecin afin qu’il constate l’ampleur de l’insuffisance pondérale "
					+ "et en recherche la cause éventuelle. Il pourra également vous faire "
					+ "une proposition de traitement.";
		}
	},
	LEANNESS {
		@Override
		public String toString() {
			return "maigreur";
		}
		@Override
		public String getAdvice(){
			return MALNUTRITION.getAdvice();
		}
	},
	NORMAL {
		@Override
		public String toString() {
			return "corpulence normale";
		}
		@Override
		public String getAdvice(){
			return "Une alimentation équilibrée, sans excès "
					+ "de matières grasses, et une activité physique "
					+ "régulière comme la marche, la natation ou le vélo "
					+ "vous aideront à maintenir votre poids idéal.";
		}
	},
	OVERWEIGHT {
		@Override
		public String toString() {
			return "surpoids";
		}
		@Override
		public String getAdvice(){
			return "Ce surpoids peut augmenter votre risque "
					+ "de maladies, en particulier votre risque "
					+ "de troubles cardiaques ou vasculaires et "
					+ "votre risque de diabète. Ce risque peut "
					+ "se cumuler avec d’autres risques provoqués "
					+ "par le tabagisme, l’hypertension ou le cholestérol."
					+ " Si vous présentez déjà un autre facteur de risque,"
					+ " une diminution de poids vous sera bénéfique. "
					+ "Une alimentation moins riche en graisses, avec "
					+ "d’avantage de fruits et de légumes, et une activité "
					+ "physique régulière vous permettront sans doute de "
					+ "retrouver un poids idéal. Votre médecin sera le mieux "
					+ "à même de vous donner les conseils adaptés à cet objectif.";
		}
	},
	MODERATEOBESITY {
		@Override
		public String toString() {
			return "obesité modérée";
		}
		@Override
		public String getAdvice(){
			return OVERWEIGHT.getAdvice();
		}
	},
	SEVEREOBESITY {
		@Override
		public String toString() {
			return "obesité sévère";
		}
		@Override
		public String getAdvice(){
			return "Ce surpoids peut augmenter votre risque de maladies, " +
					"en particulier de troubles cardiaques ou vasculaires et de " +
					"diabète. Ce risque peut se cumuler avec d’autres risques " +
					"provoqués par le tabagisme, l’hypertension ou le cholestérol. " +
					"Dans tous les cas, une diminution de poids vous sera bénéfique. " +
					"Une alimentation moins riche en graisses, avec d’avantage de fruits " +
					"et de légumes, et une activité physique régulière vous permettront " +
					"sans doute de retrouver un poids idéal. Votre médecin sera le mieux " +
					"à même de vous donner les conseils qui vous aideront à atteindre cet objectif.";
		}
	},
	MORBIDOBESITY {
		@Override
		public String toString() {
			return "obesité morbide";
		}
		@Override
		public String getAdvice(){
			return "Vous souffrez d’obésité, et cela signifie que vous êtes exposé " +
					"à un risque non négligeable de contracter des maladies cardiaques " +
					"(infarctus), vasculaires (accident vasculaire cérébral, insuffisance " +
					"veineuse) ou métaboliques (diabète). Cette obésité peut aussi être à " +
					"l’origine d’essoufflement, de fatigue, de douleurs dorsales ou articulaires " +
					"et difficultés psychologiques qui perturbent sérieusement vos activités " +
					"quotidiennes. Heureusement, même une perte de poids modérée (5 à 10%) peut " +
					"avoir un effet positif sur votre santé et votre mental, à condition bien sûr " +
					"de ne pas reprendre les kilos perdus. Dans cette optique, il serait judicieux " +
					"de pratiquer tous les jours un peu plus d’activité physique et de réduire la part " +
					"des graisses dans votre alimentation. En tous cas, une consultation avec votre " +
					"médecin s’impose, pour qu’il fasse le bilan de votre maladie et envisage avec " +
					"vous les méthodes de perte de poids, ainsi que les éventuels traitements possibles. " +
					"Les associations de patients peuvent aussi vous fournir des conseils et une aide " +
					"psychologique précieuse.";

		}
	};
	
	public abstract String getAdvice();
	
}
