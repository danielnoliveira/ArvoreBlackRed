package arvoreRubroNegra.ed;

import java.util.ArrayList;

public class TreeRedBlack {
	public Node raiz;
	public Node nil;
	public TreeRedBlack(){
		nil=new Node(-1);
		raiz=nil;
	}
	private int nodeHeight(Node n){
		if(n==nil)
			return 0;
		else
			return max(nodeHeight(n.getRight()),nodeHeight(n.getLeft()))+1;
	}
	private int max(int altura1, int altura2) {
		return altura1>altura2?altura1:altura2;
	}
	public void insert(int key){
		Node z=new Node(key);
		if(raiz==nil){
			raiz=z;
			z.setLeft(nil);
			z.setRight(nil);
			z.setCor(1);
			raiz.setFather(nil);
			positionNode(raiz);
		}else{
			System.out.println("inserindo "+z.getKey());
			this.insertRN(raiz, z);
			this.preOrdem();
			System.out.println("");
		}
	}
	private void insertRN(Node raiz,Node z){
		Node y=nil;
		Node x=raiz;
		while(x!=nil){
			y=x;
			if(z.getKey()<x.getKey()){
				x=x.getLeft();
			}else{
				x=x.getRight();
			}
		}
		z.setFather(y);
		if(y==nil){
			raiz=z;
		}else{
			if(z.getKey()<y.getKey()){
				y.setLeft(z);
			}else{
				y.setRight(z);
			}
		}
		z.setLeft(nil);
		z.setRight(nil);
		z.setCor(0);
		positionNode(y);
		positionNode(z);
		insertRNfixUp(raiz,z);
	}
	private void insertRNfixUp(Node raiz,Node z){
		Node b=z;
		while(b.getFather().getCor()==0){
			
			if(b.getFather()==b.getFather().getFather().getLeft()){
				Node y=b.getFather().getFather().getRight();
				if(y.getCor()==0){
					b.getFather().setCor(1);
					y.setCor(1);
					b.getFather().getFather().setCor(0);
					b=b.getFather().getFather();//
				}else{
					if(b==b.getFather().getRight()){
						b=b.getFather();//
						leftRotate(this.raiz,b);
					}
					b.getFather().setCor(1);
					b.getFather().getFather().setCor(0);
					rightRotate(this.raiz,b.getFather().getFather());
				}
			}else{
				Node y=b.getFather().getFather().getLeft();
				if(y.getCor()==0){
					b.getFather().setCor(1);
					y.setCor(1);
					b.getFather().getFather().setCor(0);
					b=b.getFather().getFather();//
				}else{
					if(b==b.getFather().getLeft()){
						b=b.getFather();//
						rightRotate(raiz,b);
					}
					b.getFather().setCor(1);
					b.getFather().getFather().setCor(0);
					leftRotate(this.raiz,b.getFather().getFather());
				}
			}
		}
		this.raiz.setCor(1);
		positionNode(b);
		positionNode(raiz);
	}
	private void rightRotate(Node raiz, Node x) {
		Node y=x.getLeft();
		x.setLeft(y.getRight());
		y.getRight().setFather(x);
		y.setFather(x.getFather());
		if(x.getFather()==nil){
			this.raiz=y;
		}else{
			if(x==x.getFather().getRight()){
				x.getFather().setRight(y);
			}else{
				x.getFather().setLeft(y);
			}
		}
		y.setRight(x);
		x.setFather(y);
		
		positionNode(y);
		positionNode(x);
	}
	private void leftRotate(Node raiz, Node x) {
		Node y=x.getRight();
		x.setRight(y.getLeft());
		y.getLeft().setFather(x);
		y.setFather(x.getFather());
		if(x.getFather()==nil){
			this.raiz=y;
		}else{
			if(x==x.getFather().getLeft()){
				x.getFather().setLeft(y);
			}else{
				x.getFather().setRight(y);
			}
		}
		y.setLeft(x);
		x.setFather(y);
		positionNode(y);
		positionNode(x);
	}
	private Node searchNode(Node raiz,int k){
		if(raiz!=nil){
			if(raiz.getKey()==k){
				return raiz;
			}else{
				if(raiz.getKey()<k)
					return searchNode(raiz.getRight(), k);
				else
					return searchNode(raiz.getLeft(), k);
			}
		}
		return null;
	}
	private void rb_transplant(Node u,Node v){
		if(u.getFather()==nil){
			this.raiz=v;
		}else if(u==u.getFather().getLeft()){
			u.getFather().setLeft(v);
		}else {
			u.getFather().setRight(v);
		}
		v.setFather(u.getFather());
		
	}
	public void remove(int k){
		Node z=searchNode(this.raiz, k);
	
		if(z!=null){
			System.out.println("removendo "+z.getKey());
			rb_delete(z);
			this.preOrdem();
			System.out.println("");
		}else{
			System.out.println("nó não existe!!");
		}
	}
	private void rb_delete(Node z){
		Node y=z;
		Node x;
		int y_cor_original=y.getCor();
		if(z.getLeft()==nil){
			x=z.getRight();
			rb_transplant(z, z.getRight());
		}else if(z.getRight()==nil){
			x=z.getLeft();
			rb_transplant(z, z.getLeft());
		}else {
			y=tree_minimum(z.getRight());
			y_cor_original=y.getCor();
			x=y.getRight();
			if(y.getFather()==z){
				x.setFather(y);
			}else{
				rb_transplant(y, y.getRight());
				y.setRight(z.getRight());
				y.getRight().setFather(y);
			}
			rb_transplant(z, y);
			y.setLeft(z.getLeft());
			y.getLeft().setFather(y);
			y.setCor(z.getCor());
		}
		if(y_cor_original==1){
			rb_delete_fixup(x);
		}
	}
	private Node tree_minimum(Node x) {
		while(x.getLeft()!=nil){
			x=x.getLeft();
		}
		return x;
	}
	private void rb_delete_fixup(Node x){
		Node w;
		while(x!=raiz && x.getCor()==1) {
			if(x==x.getFather().getLeft()) {
				w=x.getFather().getRight();
				if(w.getCor()==0){
					w.setCor(1);//caso 1
					x.getFather().setCor(0);//caso 1
					leftRotate(this.raiz,x.getFather());//caso 1
					w=x.getFather().getRight();//caso 1
				}
				if(w.getLeft().getCor()==1 && w.getRight().getCor()==1){
					w.setCor(0);//caso 2
					x=x.getFather();//caso 2
				}else if(w.getRight().getCor()==1){
					w.getLeft().setCor(1);//caso 3
					w.setCor(0);//caso 3
					rightRotate(this.raiz, w);//caso 3
					w=x.getFather().getRight();//caso 3
				}
				w.setCor(x.getFather().getCor());//caso 4
				x.getFather().setCor(1);//caso 4
				w.getRight().setCor(1);//caso 4
				leftRotate(this.raiz, x.getFather());//caso 4
				x=this.raiz;//caso 4
					
				
			}else {
				w=x.getFather().getLeft();
				if(w.getCor()==0){
					w.setCor(1);//caso 1
					x.getFather().setCor(0);//caso 1
					rightRotate(this.raiz,x.getFather());//caso 1
					w=x.getFather().getLeft();//caso 1
				}
				if(w.getLeft().getCor()==1 && w.getRight().getCor()==1){
					w.setCor(0);//caso 2
					x=x.getFather();//caso 2
				}else if(w.getLeft().getCor()==1){
					w.getRight().setCor(1);//caso 3
					w.setCor(0);//caso 3
					leftRotate(this.raiz, w);//caso 3
					w=x.getFather().getLeft();//caso 3
				}
				w.setCor(x.getFather().getCor());//caso 4
				x.getFather().setCor(1);//caso 4
				w.getLeft().setCor(1);//caso 4
				rightRotate(this.raiz, x.getFather());//caso 4
				x=this.raiz;//caso 4
			}
		}
		x.setCor(1);
	}
	private Node sucessor(Node z){
		Node suc;
		if(z.getRight()==nil){
			return nil;
		}else{
			suc=z.getRight();
			if(suc.getLeft()==nil){
				return suc;
			}else{
				while(suc.getLeft()!=nil)
					suc=suc.getLeft();
			}
		}
		return suc;
	}
	public void preOrdem(){
		this.preOrdemZ(this.raiz);
	}
	private void preOrdemZ(Node raiz){
		if(raiz!=null && raiz.getKey()!=-1){
			System.out.println(raiz.getKey()+"---"+((raiz.getCor()==1)?"black":"red")+"---"+(nodeHeight(raiz)-1));
			this.preOrdemZ(raiz.getLeft());
			this.preOrdemZ(raiz.getRight());
		}
	}
	private void preOrdemZX(Node raiz,ArrayList<Node> vetor){
		if(raiz!=null && raiz.getKey()!=-1){
			vetor.add(raiz);
			this.preOrdemZX(raiz.getLeft(),vetor);
			this.preOrdemZX(raiz.getRight(),vetor);
		}
	}
	//em construção
	public void displayTree(){
		ArrayList<Node> vetor=new ArrayList<Node>();
		preOrdemZX(raiz,vetor);
		int max=0;
		for (Node node : vetor) {
			if(max<node.getPosition())
				max=node.getPosition();
		}
		System.out.println(max);
		Integer[] vx=new Integer[max];
		for(Node node : vetor){
			vx[node.getPosition()-1]=node.getKey();
		}
		int h=1;
		int c=0;
		int altura=nodeHeight(raiz);
		for (int i = 0; i < vx.length; i++) {
			if(h==Math.pow(2, c)){
				for (int j = 0; j <1+(altura*2); j++) {
					System.out.print(" ");
				}
				c++;
			}
			if(vx[i]!=null)System.out.print(vx[i]);
			else System.out.print("    ");
			if(h==Math.pow(2,c-1))
				System.out.println();
			h++;
			
		}
		
	}
	private void positionNode(Node d){
		if(d.getFather()==nil){
			d.setPosition(1);
		}else{
			if(d.getFather().getKey()<d.getKey())
				d.setPosition(d.getFather().getPosition()*2+1);
			else
				d.setPosition(d.getFather().getPosition()*2);
		}
	}
	private int nodeN(Node raiz){
		if(raiz==nil)
			return 0;
		else
			return 1+nodeN(raiz.getLeft())+nodeN(raiz.getRight());
	}
}
