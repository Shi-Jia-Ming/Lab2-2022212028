import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 *
 * 给定一组 n 人（编号为 1, 2, ..., n）， 我们想把每个人分进任意大小的两组。每个人都可能不喜欢其他人，那么他们不应该属于同一组。
 *
 * 给定整数 n 和数组 dislikes ，其中 dislikes[i] = [ai, bi] ，表示不允许将编号为 ai 和  bi的人归入同一组。当可以用这种方法将所有人分进两组时，返回 true；否则返回 false。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 4, dislikes = [[1,2],[1,3],[2,4]]
 * 输出：true
 * 解释：group1 [1,4], group2 [2,3]
 * 示例 2：
 *
 * 输入：n = 3, dislikes = [[1,2],[1,3],[2,3]]
 * 输出：false
 * 示例 3：
 *
 * 输入：n = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
 * 输出：false
 *
 *
 * 提示：
 *
 * 1 <= n <= 2000
 * 0 <= dislikes.length <= 104
 * dislikes[i].length == 2
 * 1 <= dislikes[i][j] <= n
 * ai < bi
 * dislikes 中每一组都 不同
 *
 */
class Solution9 {

    public boolean possibleBipartition(int n, int[][] dislikes) {
        int[] fa = new int[n + 1];
        Arrays.fill(fa, -1);

        // Initialize adjacency list for graph
        List<Integer>[] g = new List[n + 1];
        for (int i = 1; i <= n; ++i) {
            g[i] = new ArrayList<>();
        }

        // Build graph
        for (int[] p : dislikes) {
            g[p[0]].add(p[1]);
            g[p[1]].add(p[0]);
        }

        // Process each node and its connections
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j < g[i].size(); ++j) {
                // Union the first person with others in the same group
                unit(g[i].get(0), g[i].get(j), fa);
                // If two people are connected, bipartition is not possible
                if (isConnect(i, g[i].get(j), fa)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Union-Find function: Union two nodes
    public void unit(int x, int y, int[] fa) {
        x = findFa(x, fa);
        y = findFa(y, fa);
        if (x == y) {
            return;
        }
        if (fa[x] <= fa[y]) {
            fa[y] = x;
        } else {
            fa[x] = y;
        }
    }

    // Union-Find function: Find the root of a node
    public int findFa(int x, int[] fa) {
        if (fa[x] < 0) {
            return x;
        } else {
            fa[x] = findFa(fa[x], fa);
            return fa[x];
        }
    }

    // Check if two nodes are connected (in the same component)
    public boolean isConnect(int x, int y, int[] fa) {
        return findFa(x, fa) == findFa(y, fa);
    }
}