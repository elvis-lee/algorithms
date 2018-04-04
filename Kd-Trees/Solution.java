/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution 
{ 
    public static class TreeNode 
    {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    private static int buildTreeSum(TreeNode t)
    {
        if (t == null) return 0;
        else t.val += buildTreeSum(t.left) + buildTreeSum(t.right);
        return t.val;
    }
    
    public int findTilt(TreeNode root) 
    {
        buildTreeSum(root);
        return findTilt2(root);
    }
    private int findTilt2(TreeNode t)
    {
        if (t == null) return 0;
        return (findTilt2(t.left) + findTilt2(t.right) + Math.abs((t.left == null ? 0 : t.left.val) - (t.right == null ? 0 : t.right.val)));
    }   
    public static void main(String[] args)
    {
        TreeNode t1000 = new TreeNode(1000);
        TreeNode t100 = new TreeNode(100);
        TreeNode t10 = new TreeNode(10);
        TreeNode t1 = new TreeNode(1);
        t10.left = t1000;
        t1.left = t10;
        t1.right = t100;
        buildTreeSum(t1);
        System.out.println(new Solution().findTilt2(t1));
        
    }
    
}