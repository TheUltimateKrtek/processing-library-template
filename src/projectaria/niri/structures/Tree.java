package projectaria.niri.structures;

import java.util.ArrayList;

public class Tree<T> extends Network<T>{
  public Tree(T object){
    super(object);
  }
  public Tree(){
    super();
  }

  public boolean canBeAddedAsInput(Network<T> o){
    if(o.getClass().isAssignableFrom(Tree.class)){
      return false;
    }

    Tree<T> treeObject = (Tree<T>)o;
    
    if(this.isAncestorOf(treeObject)) return false;

    if(this.getInputCount() == 1) this.removeInput(0);
    return true;
  }
  public boolean canBeAddedAsOutput(Network<T> o){
    return true;
  }

  final public Tree<T> getRoot(){
    Tree<T> o = this;
    while(o.getInput(0) != null) o = (Tree<T>)o.getInput(0);
    return o;
  }
  final public Tree[] getLeaves(boolean recursive){
    if(this.getOutputCount() == 0) return new Tree[]{this};
    ArrayList<Tree<T>> al = new ArrayList<Tree<T>>();
    
    if(recursive){
      for(int i = 0; i < this.getOutputCount(); i ++){
        Tree[] rl = this.getBranch(i).getLeaves(true);
        for(Tree<T> ato:rl) al.add(ato);
      }
    }
    else{
      for(int i = 0; i < this.getOutputCount(); i ++){
        if(this.getBranch(i).isLeaf()) al.add(this.getBranch(i));
      }
    }
    
    Tree[] rl = new Tree[al.size()];
    for(int i = 0; i < rl.length; i ++) rl[i] = al.get(i);
    return rl;
  }
  final public Tree<T> getParent(){
    if(this.getInputCount() == 0) return null;
    return (Tree<T>)this.getInput(0);
  }
  final public Tree<T> getBranch(int i){
    if(i < 0 || i > this.getOutputCount()) return null;
    return (Tree<T>)this.getOutput(i);
  }
  final public Tree[] getParentHiearchy(){
    if(this.isRoot()) return new Tree[]{this};

    Tree[] output = this.getParent().getParentHiearchy();
    Tree[] rl = new Tree[output.length + 1];
    for(int i = 0; i < output.length; i ++) rl[i] = output[i];
    rl[output.length] = this;
    return rl;
  }

  final public boolean isLeaf(){
    return this.getOutputCount() == 0;
  }
  final public boolean isRoot(){
    return this.getParent() == null;
  }
  final public boolean isAncestorOf(Tree<T> o){
    if(o == null || o == this) return false;
    Tree[] parents = o.getParentHiearchy();
    for(Tree<T> ato:parents) if(ato == this && ato != o) {
      return true;
    }
    return false;
  }
}