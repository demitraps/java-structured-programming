# Project 06 - LargestSumContiguousSubArrayApp

The algorithm used in the provided code is known as **Kadane's algorithm**, and it is used to find the largest sum of a contiguous sub-array within a given array.

The app works as follows:

1.  It begins by performing **initial checks** on the input array:

    -   If the array is **empty**, it prints a message and exits.
    -   It counts the number of positive and negative elements in the array.

2.  Next, it handles **special cases** where all elements in the array are either non-negative or non-positive:

    -   If all elements are **non-negative**, it prints a message stating that the maximum sub-array is the entire array. It also prints the sum of the array and exits.
    -   If all elements are **non-positive**, it prints a message stating that the solution is any sub-array of size one, containing the maximal value of the array. It also prints the maximum value and exits.

3.  If **none of the special cases are true**, the app proceeds to find the largest sum of a contiguous sub-array using Kadane's algorithm:

    -   It iterates through the array, maintaining two variables: localMaximum and globalMaximum.
    -   localMaximum keeps track of the maximum sum of a sub-array ending at the current position.
    -   globalMaximum stores the overall maximum sum found so far.
    -   At each iteration, the algorithm adds the current element to localMaximum and checks if it surpasses globalMaximum. If it does, it updates globalMaximum and stores the start and end indices of the sub-array.
    -   If localMaximum becomes negative, it resets it to zero and updates the pivot index.

4.  Finally, the algorithm prints the largest sum, the starting index, and the ending index of the sub-array with the maximum sum.


## Example

To illustrate the algorithm with an example, let's consider the input array {-2, 1, -3, 4, -1, 2, 1, -5, 4}:

-   Initially, the algorithm checks if the array contains all non-negative or non-positive numbers, which is not the case.

-   It then iterates through the array, updating localMaximum and globalMaximum as follows:

    -   At index 0: localMaximum = -2, globalMaximum = -2
    -   At index 1: localMaximum = 1, globalMaximum = 1
    -   At index 2: localMaximum = -2, globalMaximum = 1
    -   At index 3: localMaximum = 4, globalMaximum = 4 (update start and end indices)
    -   At index 4: localMaximum = 3, globalMaximum = 4
    -   At index 5: localMaximum = 5, globalMaximum = 5 (update start and end indices)
    -   At index 6: localMaximum = 6, globalMaximum = 6 (update start and end indices)
    -   At index 7: localMaximum = 1, globalMaximum = 6
    -   At index 8: localMaximum = 5, globalMaximum = 6
-   The algorithm then prints the largest sum (6) and the indices of the sub-array ({4, -1, 2, 1}).


## Time Complexity O(n)

Kadane's algorithm is able to solve the problem of finding the largest sum of a contiguous sub-array in **O(n) time complexity**, where n is the size of the input array. The algorithm achieves this efficiency by using a single pass through the array.

More specifically:

1.  It performs a single loop over the input array: The algorithm iterates through each element of the array **exactly once**, from the first element to the last. This loop contributes a time complexity of O(n), where n is the size of the array.

2.  It maintains constant-time operations within the loop: Within the loop, the algorithm performs a series of constant-time operations, such as addition, comparison, and updating variables. These operations do not depend on the size of the input array and can be considered as constant-time operations.

3.  It avoids unnecessary computations: Kadane's algorithm avoids unnecessary computations by discarding sub-arrays with negative sums.
> When the sum of a sub-array becomes negative, the algorithm resets the localMaximum variable to zero and updates the pivot index.
This allows the algorithm to skip sub-arrays that cannot contribute to the largest sum.
   