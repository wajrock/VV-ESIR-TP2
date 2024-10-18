# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

A refresher on TCC and LCC is available in the [course notes](https://oscarlvp.github.io/vandv-classes/#cohesion-graph).

## Answer

- When all methods of a class share same attributes between them so the TCC and LCC produce the same value.

- Example code

````
class Example{
    private:
        int nbBooks;
        int nbAlbums;
        int nbEntities;
    public:
        int getNumberEntities(){
            return this.nbBooks + this.nbAlbums;
        }

        int getNbBooks(){
            return nbEntities - this.nbBooks;
        }

        int getNbAlbums(){
            return nbEntities - this.nbAlbums;
        }

        void updateEntities(int numberNewBooks, int numberNewAlbums){
            this.nbBooks += numberNewBooks;
            this.nbAlbums += numberNewAlbums;
        }

}
````

    |----------------------- nbEntities --------------------------|
    |                                                             |
[getNbAlbums] -- nbAlbums -- [updateEntities] -- nbBooks -- [getNbBooks]
        \                    |                               /
         \                   |                              /
          \           nbBooks,nbAlbums                     /
           \                 |                            /
            \                |                           /
             -- nbAlbums -- [getNumberEntities]- nbBooks - 

    TCC = LCC = 6 / 6

- LCC can't be lower than TCC because LCC is based on TCC's number of branches and add new branches by transitivity.