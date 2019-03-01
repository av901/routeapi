# routeapi
Optimal Route Finder

Example REST calls to routeapi:

1. Create New Route.
url: http://localhost:8080/route/addRoute
method: post
body: 
{"nodes" : ["A", "B", "C", "D", "E", "F"], "edges" : [["A", "B", 12.1, 1.0], ["C", "B", 12.9, 1.0], ["D", "C", 1.2, 1.0], ["A", "D", 12.0, 10.1], ["A", "F", 21.1, 1.0], ["F", "B", 14.1, 3.0], ["D", "E", 4.0, 2.0], ["C", "E", 1.5, 1.0]]}

2. Add new node
url: http://localhost:8080/route/addNode
method: post
body:
{"route_id" : 1, "node_name" : "G", "adj_nodes" : {"A": [10,1],"B":[13,2],"C" : [14, 4]}}

3. Delete nodes
url: http://localhost:8080/route/deleteNode
method: post
body:
{"nodes" : ["A", "B"], "route_id":1}

4. Update Edges
url: http://localhost:8080/route/updateEdge
method: post
body:
{"route_id":1, "edges": [["A", "B", 18,8],["B", "C", 17,1],["C","D", 21,4]]}

5. Optimal Path
url: http://localhost:8080/route/optimalPath
method: post
body:
{"route_id":1, "source": "A", "destination" : "F"}
response:
{"nodes": ["A", "B", "F"], "totalCost": 16.8 }
