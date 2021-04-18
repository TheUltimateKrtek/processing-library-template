package projectaria.niri.structures;

import java.util.ArrayList;

public class Network<T>{
  private T object;
  private ArrayList<Network<T>> inputObjects;
  private ArrayList<Network<T>> outputObjects;

  public Network(T object){
    this.object = object;
    this.inputObjects = new ArrayList<Network<T>>();
    this.outputObjects = new ArrayList<Network<T>>();
  }
  public Network(){
    this.object = null;
    this.inputObjects = new ArrayList<Network<T>>();
    this.outputObjects = new ArrayList<Network<T>>();
  }

  public T getObject(){
    return this.object;
  }

  final public int getInputCount(){
    return this.inputObjects.size();
  }
  final public int getOutputCount(){
    return this.outputObjects.size();
  }

  final public Network<T> getInput(int i){
    if(i < 0 || i >= this.getInputCount()) return null;
    return this.inputObjects.get(i);
  }
  final public Network<T> getOutput(int i){
    if(i < 0 || i >= this.getOutputCount()) return null;
    return this.outputObjects.get(i);
  }
  final public boolean containsInput(Network<T> o){
    return this.inputObjects.contains(o);
  }
  final public boolean containsOutput(Network<T> o){
    return this.outputObjects.contains(o);
  }
  final public int getInputIndex(Network<T> o){
    if(o == null) return -1;
    for(int i = 0; i < this.getInputCount(); i ++){
      if(this.getInput(i) == o) return i;
    }
    return -1;
  }
  final public int getOutputIndex(Network<T> o){
    if(o == null) return -1;
    for(int i = 0; i < this.getOutputCount(); i ++){
      if(this.getOutput(i) == o) return i;
    }
    return -1;
  }

  final public Network<T> addInput(Network<T> o){
    if(o == null) return this;
    return this.addInput(o, this.getInputCount(), o.getOutputCount());
  }
  final public Network<T> addInput(Network<T> o, int indexOutput){
    if(o == null) return this;
    return this.addInput(o, indexOutput, o.getOutputCount());
  }
  final public Network<T> addInput(Network<T> o, int indexInput, int indexOutput){
    if(o == null) return this;
    boolean containedIn = this.containsInput(o);
    if(containedIn) return this;
    boolean containedOut = o.containsOutput(this);
    if(containedOut && !containedIn){
      this.forceAddInput(o, indexInput);
      return this;
    }
    if(this.canBeAddedAsInput(o) && o.canBeAddedAsOutput(this)){
      this.forceAddInput(o, indexInput);
      o.addOutput(this, indexOutput, indexInput);
      o.onOutputAdded(this, indexOutput);
      this.onInputAdded(o, indexInput);
      return this;
    }
    return this;
  }
  private void forceAddInput(Network<T> o, int index){
    this.inputObjects.add(index < 0 ? 0 : (index > this.getOutputCount() ? this.getOutputCount() : index), o);
  }
  protected boolean canBeAddedAsInput(Network<T> o){
    return true;
  }
  final public Network<T> addOutput(Network<T> o){
    if(o == null) return this;
    return this.addOutput(o, this.getOutputCount(), o.getInputCount());
  }
  final public Network<T> addOutput(Network<T> o, int indexOutput){
    if(o == null) return this;
    return this.addOutput(o, indexOutput, o.getInputCount());
  }
  final public Network<T> addOutput(Network<T> o, int indexOutput, int indexInput){
    if(o == null) return this;
    boolean containedOut = this.containsOutput(o);
    if(containedOut) return this;
    boolean containedIn = o.containsInput(this);
    if(containedIn && !containedOut){
      this.forceAddOutput(o, indexOutput);
      return this;
    }
    if(this.canBeAddedAsOutput(o) && o.canBeAddedAsInput(this)){
      this.forceAddOutput(o, indexOutput);
      o.addInput(this, indexInput, indexOutput);
      this.onOutputAdded(o, indexOutput);
      o.onInputAdded(this, indexInput);
      return this;
    }
    return this;
  }
  private void forceAddOutput(Network<T> o, int index){
    this.outputObjects.add(index < 0 ? 0 : (index > this.getOutputCount() ? this.getOutputCount() : index), o);
  }
  protected boolean canBeAddedAsOutput(Network<T> o){
    return true;
  }

  final public Network<T> removeInput(Network<T> o){
    this.popInput(o);
    return this;
  }
  final public Network<T> removeInput(int index){
    this.popInput(index);
    return this;
  }
  final public Network<T> popInput(Network<T> o){
    return this.popInput(this.getInputIndex(o));
  }
  final public Network<T> popInput(int index){
    if(index < 0 || index >= this.getInputCount()) return null;
    Network<T> o = this.getInput(index);
    if(o.containsOutput(this)) o.removeOutput(this);
    else{
      this.forceRemoveInput(index);
      this.onInputRemoved(o, index);
    }
    return o;
  }
  private void forceRemoveInput(int index){
    this.inputObjects.remove(index);
  }
  final public Network<T> removeOutput(Network<T> o){
    this.popOutput(o);
    return this;
  }
  final public Network<T> removeOutput(int index){
    this.popOutput(index);
    return this;
  }
  final public Network<T> popOutput(Network<T> o){
    return this.popOutput(this.getOutputIndex(o));
  }
  final public Network<T> popOutput(int index){
    if(index < 0 || index >= this.getOutputCount()) return null;
    Network<T> o = this.getOutput(index);
    
    this.forceRemoveOutput(index);
    o.removeInput(this);
    this.onOutputRemoved(o, index);
    
    return o;
  }
  private void forceRemoveOutput(int index){
    this.outputObjects.remove(index);
  }

  public Network<T> moveInput(Network<T> o, int to){
    if(o == null) return this;
    return this.moveInput(this.getInputIndex(o), to);
  }
  public Network<T> moveInput(int from, int to){
    if(from < 0 || from >= this.getInputCount()) return this;
    if(from == to) return this;
    Network<T> o = this.getInput(from);
    if(!this.canInputBeMoved(o, from, to)) return this;
    if(to > from) to --;
    this.forceRemoveInput(from);
    this.forceAddInput(o, to);
    this.onInputMoved(o, from, to);
    return this;
  }
  protected boolean canInputBeMoved(Network<T> o, int from, int to){
    return false;
  }
  public Network<T> moveOutput(Network<T> o, int to){
    if(o == null) return this;
    return this.moveOutput(this.getInputIndex(o), to);
  }
  public Network<T> moveOutput(int from, int to){
    if(from < 0 || from >= this.getOutputCount()) return this;
    if(from == to) return this;
    Network<T> o = this.getInput(from);
    if(!this.canOutputBeMoved(o, from, to)) return this;
    if(to > from) to --;
    this.forceRemoveOutput(from);
    this.forceAddOutput(o, to);
    this.onOutputMoved(o, from, to);
    return this;
  }
  protected boolean canOutputBeMoved(Network<T> o, int from, int to){
    return false;
  }

  protected void onInputAdded(Network<T> o, int index){
  }
  protected void onOutputAdded(Network<T> o, int index){
  }
  protected void onInputRemoved(Network<T> o, int index){
  }
  protected void onOutputRemoved(Network<T> o, int index){
  }
  protected void onInputMoved(Network<T> o, int from, int to){
  }
  protected void onOutputMoved(Network<T> o, int from, int to){
  }
}

