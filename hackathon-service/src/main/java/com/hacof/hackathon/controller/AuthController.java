package com.hacof.hackathon.controller;

// @RestController
// @RequestMapping("/api/v1/auth")
// @RequiredArgsConstructor
// public class AuthController {
//
//    final AuthenticationManager authenticationManager;
//
//
//    final UserDetailsService userDetailsService;
//
//
//    final JwtUtil jwtUtil;
//
//    final PasswordEncoder passwordEncoder;
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//            );
//
//            String token = jwtUtil.generateToken(authentication);
//            return ResponseEntity.ok(new AuthResponse(token));
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(401).body(new AuthResponse("Invalid username or password"));
//        }
//    }
// }
