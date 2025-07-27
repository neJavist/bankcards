package org.example.bankcards.controller;

import org.example.bankcards.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        String token = tokenProvider.generateToken((UserDetails) auth.getPrincipal());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

