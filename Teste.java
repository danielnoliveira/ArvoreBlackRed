package arvoreRubroNegra.ed;

public class Teste {
	public static void main(String[] args) {
		TreeRedBlack tree=new TreeRedBlack();
		
		tree.insert(11);
		tree.insert(2);
		tree.insert(14);
		tree.insert(1);
		tree.insert(7);
		tree.insert(5);
		tree.insert(8);
		tree.insert(15);
		tree.insert(4);
		tree.remove(5);
		tree.remove(7);
		
		/*
		tree.insert(80);
		tree.insert(90);
		tree.insert(27);
		tree.insert(8);
		tree.insert(60);
		tree.insert(25);
		tree.preOrdem();
		System.out.println("");
		tree.remove(15);
		tree.preOrdem();
		System.out.println("");
		tree.remove(20);
		tree.preOrdem();
		System.out.println("");
		tree.remove(25);
		tree.preOrdem();
		System.out.println("");
		tree.remove(30);
		tree.preOrdem();
		
		System.out.println("");
		tree.remove(8);
		tree.preOrdem();
		System.out.println("");
		tree.remove(15);
		tree.preOrdem();
		
		*/
		//tree.displayTree();
	}
}
